package cn.echcz.webservice.usecase;

import cn.echcz.webservice.entity.Entity;
import cn.echcz.webservice.usecase.repository.BaseRepository;
import cn.echcz.webservice.usecase.repository.DataUpdater;
import cn.echcz.webservice.usecase.repository.Querier;
import cn.echcz.webservice.usecase.repository.QueryFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 抽象的实体操作用例，
 * <br/>对实体进行CRUD操作
 *
 * @param <K> 实体ID类型
 * @param <E> 实体类型
 * @param <U> 数据更新器类型
 * @param <F> 数据查询器类型
 * @param <R> 数据仓库类型
 */
public abstract class AbstractEntityUsecase<K, E extends Entity<K>,
        U extends DataUpdater, F extends QueryFilter<?>,
        R extends BaseRepository<K, E, U, F>>
        extends AbstractUsecase {

    /**
     * 实体仓库
     */
    protected abstract R getRepository();

    /**
     * 根据实体ID进行过滤
     *
     * @param filter 过滤器
     * @param id     实体ID
     */
    protected abstract void filterById(F filter, K id);

    /**
     * 使用实体ID集进行过滤
     *
     * @param filter 过滤器
     * @param ids    实体ID
     */
    protected abstract void filterByIds(F filter, Collection<K> ids);

    /**
     * 在添加实体之前检查实体信息
     * <br/>NOTE: 并发写安全
     * @return 如果通过检查，返回true；如果需要忽略本次操作，返回false
     * @throws Exception 如果实体信息违反添加实体的业务规定
     */
    protected abstract boolean checkEntityForAdd(E entity);

    /**
     * 添加实体
     *
     * @return 添加的实体ID
     */
    public K add(E entity) {
        if (!checkEntityForAdd(entity)) {
            return null;
        }
        return getRepository().add(entity);
    }

    /**
     * 在更新实体之前检查实体信息
     * <br/>NOTE: 并发写安全
     * @return 如果通过检查，返回true；如果需要忽略本次操作，返回false
     * @throws Exception 如果实体信息违反更新实体的业务规定
     */
    protected abstract boolean checkEntityForUpdate(E entity);

    /**
     * 根据实体信息更新（仓库里的）实体
     *
     * @param updater 数据更新器
     * @param entity  实体信息
     */
    protected abstract void updateByEntity(U updater, E entity);

    /**
     * 更新实体
     *
     * @return 更新的数量
     */
    public boolean update(E entity) {
        if (!checkEntityForUpdate(entity)) {
            return false;
        }
        return getRepository().update(u -> updateByEntity(u, entity), f -> filterById(f, entity.getId())) > 0;
    }

    /**
     * 删除指定实体
     *
     * @return 删除的数量
     */
    public int deleteByIds(Collection<K> ids) {
        return getRepository().delete(f -> filterByIds(f, ids));
    }

    /**
     * 删除指定实体列表
     *
     * @return 删除的数量
     */
    public int deleteByIds(K... ids) {
        return deleteByIds(Arrays.asList(ids));
    }

    /**
     * 获取指定实体
     */
    public Optional<E> getById(K id) {
        Querier<E, F> querier = getRepository().querier();
        querier.filter(f -> filterById(f, id));
        return querier.get();
    }

    /**
     * 获取指定实体列表
     */
    public List<E> listByIds(Collection<K> ids) {
        Querier<E, F> querier = getRepository().querier();
        querier.filter(f -> filterByIds(f, ids));
        return querier.list();
    }

    /**
     * 获取指定实体列表
     */
    public List<E> listByIds(K... ids) {
        return listByIds(Arrays.asList(ids));
    }

    /**
     * 生成数据查询器以让调用者自定义数据查询
     */
    public Querier<E, F> querier() {
        return getRepository().querier();
    }
}
