package cn.echcz.webservice.usecase.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 数据仓库基本接口
 *
 * @param <K> 主键类型
 * @param <D> 数据类型
 * @param <U> 数据更新器类型
 * @param <F> 查询过滤器类型
 */
public interface BaseRepository<K, D, U extends DataUpdater, F extends QueryFilter<?>> {
    /**
     * 添加一条数据
     *
     * @param data 数据
     * @return 被添加的数据的主键
     */
    K add(D data);

    /**
     * 更新数据
     *
     * @param updater 用于设置更新后的数据值
     * @param filter  用于设置查询待更新数据的过滤条件
     * @return 已更新的数据量
     */
    int update(Consumer<U> updater, Consumer<F> filter);

    /**
     * 删除数据
     *
     * @param filter 用于设置查询待删除数据的过滤条件
     * @return 已删除的数据量
     */
    int delete(Consumer<F> filter);

    /**
     * 获取某条数据，
     * 保证在并发写时是安全的，如持有写锁后进行查询
     *
     * @param filter 用于设置查询待的过滤条件
     */
    Optional<D> getForWrite(Consumer<F> filter);

    /**
     * 获取数据列表，
     * 保证在并发写时是安全的，如持有写锁后进行查询
     *
     * @param filter 用于设置查询待的过滤条件
     */
    List<D> listForWrite(Consumer<F> filter);

    /**
     * 获取数据计数，
     * 保证在并发写时是安全的，如持有写锁后进行查询
     *
     * @param filter 用于设置查询待的过滤条件
     */
    int countForWrite(Consumer<F> filter);

    /**
     * 查询数据是否存在，
     * 保证在并发写时是安全的，如持有写锁后进行查询
     *
     * @param filter 用于设置查询待的过滤条件
     */
    boolean isExistedForWrite(Consumer<F> filter);

    /**
     * 生成查询器以进行数据查询
     */
    Querier<D, F> querier();
}
