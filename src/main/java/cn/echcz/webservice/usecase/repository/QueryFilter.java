package cn.echcz.webservice.usecase.repository;

import java.util.function.Consumer;

/**
 * 查询过滤器
 * @param <T> 子过滤器类型
 */
public interface QueryFilter<T extends QueryFilter<?>> {
    /**
     * 与 过滤条件
     * @param filter 用于设置过滤条件
     */
    void and(Consumer<T> filter);

    /**
     * 或 过滤条件
     * @param filter 用于设置过滤条件
     */
    void or(Consumer<T> filter);

    /**
     * 对当前过滤条件 取非
     */
    void not();
}
