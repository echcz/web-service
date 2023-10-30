package cn.echcz.webservice.usecase.repository;

import java.util.Collection;

/**
 * 查询字段
 * @param <T> 字段值类型
 */
public interface QueryField<T> {
    /**
     * 是 null 值
     */
    default void isNull() {
        throw new UnsupportedOperationException(this.getClass().getName() + ".isNull() is unsupported");
    }

    /**
     * 不是 null 值
     */
    default void notNull() {
        throw new UnsupportedOperationException(this.getClass().getName() + ".notNull() is unsupported");
    }

    /**
     * 等于
     */
    default void eq(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".eq() is unsupported");
    }

    /**
     * 不等于
     */
    default void ne(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".ne() is unsupported");
    }

    /**
     * 小于
     */
    default void lt(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".lt() is unsupported");
    }

    /**
     * 小于等于
     */
    default void le(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".le() is unsupported");
    }

    /**
     * 大于
     */
    default void gt(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".gt() is unsupported");
    }

    /**
     * 大于等于
     */
    default void ge(T value) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".ge() is unsupported");
    }

    /**
     * 属于
     */
    default void in(Collection<T> values) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".in() is unsupported");
    }

    /**
     * 不属于
     */
    default void notIn(Collection<T> values) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".notIn() is unsupported");
    }

    /**
     * 匹配
     */
    default void like(String pattern) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".like() is unsupported");
    }

    /**
     * 不匹配
     */
    default void notLike(String pattern) {
        throw new UnsupportedOperationException(this.getClass().getName() + ".notLike() is unsupported");
    }
}
