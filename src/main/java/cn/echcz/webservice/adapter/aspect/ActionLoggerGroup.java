package cn.echcz.webservice.adapter.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加操作日志 标记注解
 * 添加了此注解的方法将在执行成功返回后添加操作日志
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLoggerGroup {
    /**
     * 操作组
     */
    String value();
}
