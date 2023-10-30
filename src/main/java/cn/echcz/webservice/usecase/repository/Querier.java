package cn.echcz.webservice.usecase.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 查询器接口
 * @param <D> 数据类型
 * @param <F> 查询过滤器类型
 */
public interface Querier<D, F extends QueryFilter<?>> {
    /**
     * 过滤
     * @param filter 用于设置查询过滤条件
     * @return this
     */
    void filter(Consumer<F> filter);

    /**
     * 查询单个数据
     */
    Optional<D> get();

    /**
     * 查询数据列表
     */
    List<D> list();

    /**
     * 查询实据列表
     * @param offset 偏移量，跳过此值数量的数据
     * @param limit 限制量，查询出来的数据量不会超过此值
     */
    default List<D> list(int offset, int limit) {
        List<D> list = list();
        return list.stream().skip(offset).limit(limit).toList();
    }

    /**
     * 查询数量
     */
    default int count() {
        return list().size();
    }

    /**
     * 是否存在
     */
    default boolean isExisted() {
        return get().isPresent();
    }
}
