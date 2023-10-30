package cn.echcz.webservice.adapter.aspect;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.entity.ActionLogInitParams;
import cn.echcz.webservice.entity.ActionType;
import cn.echcz.webservice.entity.User;
import cn.echcz.webservice.usecase.ActionLogUsecase;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

@Aspect
@Component
@Slf4j
@Order(0)
public class ActionLoggerAspect {
    private final ObjectMapper objectMapper;

    @Setter(onMethod_ = @Autowired)
    private ActionLogUsecase actionLogUsecase;

    public ActionLoggerAspect() {
        objectMapper = JsonMapper.builder()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
                .addModule(new JavaTimeModule())
                .build();
    }

    @AfterReturning(value = "@annotation(actionLogger)", returning = "ret")
    public void logOperation(JoinPoint joinPoint, ActionLogger actionLogger, Object ret) {
        try {
            ActionLogInitParams actionLogInitParams = new ActionLogInitParamsWrapper(joinPoint, actionLogger, ret);
            ActionLog actionLog = ActionLog.create(actionLogInitParams);
            actionLogUsecase.loggingWithCurrentUser(actionLog);
        } catch (Exception e) {
            log.error("处理 @ActionLogger 失败", e);
        }
    }

    private String getActionGroup(ActionLogger actionLogger, Object target) {
        String group = actionLogger.group();
        if (group.isEmpty()) {
            ActionLoggerGroup actionLoggerGroup = target.getClass().getAnnotation(ActionLoggerGroup.class);
            if (Objects.nonNull(actionLoggerGroup)) {
                group = actionLoggerGroup.value();
            }
        }
        return group;
    }

    /**
     * 获取值，支持以下特性:
     * 1. . 操作符，参数名.无参方法名，如 list.size 将会调用 list的 size() 方法的返回值作为最终返回值
     * 2. List表达式: 以逗号(,)分隔的多个参数
     * 3. Map表达式: 以逗号(,)分隔的多个参数，对于每个参数，使用冒号(:)分隔，前半部分为键名，后半部分为值表达式
     * @param name 参数名
     * @param parameterNames 连接点方法参数名列表
     * @param args 连接点方法参数值列表，与 parameterNames 一一对应
     * @param ret 连接点方法返回值
     */
    private Object getValue(String name, String[] parameterNames, Object[] args, Object ret)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] names = name.split(",");
        if (name.contains(":")) {
            // Map表达式
            Map<String, Object> map = new HashMap<>(names.length);
            for (String item : names) {
                int i = item.indexOf(":");
                String k;
                Object v;
                if (i >= 0) {
                    k = item.substring(0, i);
                    v = getSimpleValue(item.substring(i+1), parameterNames, args, ret);
                } else {
                    k = item;
                    v = getSimpleValue(item, parameterNames, args, ret);
                }
                map.put(k, v);
            }
            return map;
        } else if (names.length != 1 || name.endsWith(",")) {
            // List表达式
            List<Object> list = new ArrayList<>(names.length);
            for (String item : names) {
                list.add(getSimpleValue(item, parameterNames, args, ret));
            }
            return list;
        } else {
            // 简单值
            return getSimpleValue(name, parameterNames, args, ret);
        }
    }

    /**
     * 获取简单值
     * @param name 参数名,支持 . 操作符
     * @param parameterNames 连接点方法参数名列表
     * @param args 连接点方法参数值列表，与 parameterNames 一一对应
     * @param ret 连接点方法返回值
     */
    private Object getSimpleValue(String name, String[] parameterNames, Object[] args, Object ret)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (name.isEmpty()) {
            return null;
        }
        int i = name.indexOf(".");
        String method = "";
        if (i > 0) {
            method = name.substring(i + 1);
            name = name.substring(0, i);
        }
        Object value;
        if (ActionLogger.RETURN_KEY.equals(name)) {
            value = ret;
        } else {
            value = getArg(name, parameterNames, args);
        }
        if (method.isEmpty() || Objects.isNull(value)) {
            return value;
        }
        Method m = value.getClass().getMethod(method);
        return m.invoke(value);
    }

    /**
     * 获取指定方法参数值
     * @param name 参数名
     * @param parameterNames 参数名列表
     * @param args 参数值
     */
    private Object getArg(String name, String[] parameterNames, Object[] args) {
        for (int i = 0, n = parameterNames.length; i < n; i++) {
            if (name.equals(parameterNames[i])) {
                return args[i];
            }
        }
        return null;
    }

    @RequiredArgsConstructor
    private class ActionLogInitParamsWrapper implements ActionLogInitParams {
        private final JoinPoint joinPoint;
        private final ActionLogger actionLogger;
        private final Object ret;

        @Override
        public String group() {
            return getActionGroup(actionLogger, joinPoint.getTarget());
        }
        @Override
        public String name() {
            return actionLogger.name();
        }
        @Override
        public ActionType type() {
            return actionLogger.type();
        }
        @Override
        public String content() {
            try {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                String[] parameterNames = signature.getParameterNames();
                Object[] args = joinPoint.getArgs();
                Object contentValue = getValue(actionLogger.content(), parameterNames, args, ret);
                return objectMapper.writeValueAsString(contentValue);
            } catch (Exception e) {
                log.error("处理 @ActionLogger.content() 失败: content = {}", actionLogger.content());
                throw new IllegalStateException(e);
            }
        }
        @Override
        public User actor() {
            return null;
        }

        @Override
        public LocalDateTime actionTime() {
            return LocalDateTime.now();
        }
    }
}
