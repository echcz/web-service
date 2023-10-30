package cn.echcz.webservice.adapter.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.echcz.webservice.entity.ActionType;

/**
 * 添加操作日志 标记注解
 * 添加了此注解的方法将在执行成功返回后添加操作日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLogger {
    String RETURN_KEY = "return";

    /**
     * 动作/操作组
     */
    String group() default "";
    /**
     * 动作/操作名
     */
    String name();
    /**
     * 动作/操作类型
     */
    ActionType type();
    /**
     * 操作内容 参数名。
     * 如果是返回值，则为 {@value RETURN_KEY}。
     * 支持以下特性:
     * 1. . 操作符，参数名.无参方法名，如 list.size 将会调用 list的 size() 方法的返回值作为最终返回值
     * 2. List表达式: 以逗号(,)分隔的多个参数
     * 3. Map表达式: 以逗号(,)分隔的多个参数，对于每个参数，使用冒号(:)分隔，前半部分为键名，后半部分为值表达式
     */
    String content() default "";
}
