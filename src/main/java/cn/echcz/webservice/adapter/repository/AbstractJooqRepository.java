package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.usecase.repository.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 抽象的 JOOQ 仓库
 *
 * @param <K> 主键类型
 * @param <D> 数据类型
 * @param <R> JOOQ记录类型
 * @param <U> 数据更新器类型
 * @param <F> 查询过滤器类型
 */
public abstract class AbstractJooqRepository<K, D, R extends Record,
        U extends DataUpdater, F extends QueryFilter<?>>
        implements BaseRepository<K, D, U, F> {
    /**
     * JOOQ DSL上下文
     */
    protected abstract DSLContext getDslContext();

    /**
     * JOOQ表
     */
    protected abstract Table<R> getTable();

    /**
     * JOOQ主键字段
     */
    protected abstract TableField<R, K> getPk();

    /**
     * JOOQ记录转换为数据，
     * 用于查询
     */
    protected abstract D recordToDataForQuery(R record);

    /**
     * 数据转换为JOOQ记录，
     * 用于添加
     */
    protected abstract R dataToRecordForAdd(D data);

    /**
     * 获取数据更新器
     * <br/>NOTE: 应该每次获取/创建一个新的数据更新器
     * <br/>IMPORTANT: 除 {@link NoopDataUpdater} 外，必须实现 {@link JooqRecordProvider} 接口
     */
    protected abstract U getDataUpdater();

    /**
     * 查询过滤器
     * <br/>NOTE: 应该每次获取/创建一个新的查询过滤器
     * <br/>IMPORTANT: 除 {@link NoopQueryFilter} 外，必须实现 {@link JooqConditionHolder} 接口
     */
    protected abstract F getQueryFilter();

    /**
     * 根据过滤方法获取JOOQ查询条件
     *
     * @param filter 过滤方法
     */
    protected Condition getConditionByFilter(Consumer<F> filter) {
        F queryFilter = getQueryFilter();
        if (queryFilter instanceof NoopQueryFilter) {
            return DSL.noCondition();
        }
        filter.accept(queryFilter);
        return ((JooqConditionHolder) queryFilter).getCondition();
    }

    /**
     * 查询数据的辅助方法
     */
    protected SelectConditionStep<R> select(Condition condition) {
        return getDslContext()
                .selectFrom(getTable())
                .where(condition);
    }

    /**
     * 查询数据的辅助方法
     */
    protected SelectConditionStep<R> select(Consumer<F> filter) {
        return select(getConditionByFilter(filter));
    }

    /**
     * 查询数量的辅助方法
     */
    protected SelectConditionStep<Record1<Integer>> selectCount(Condition condition) {
        return getDslContext()
                .selectCount().from(getTable())
                .where(condition);
    }

    /**
     * 查询数量的辅助方法
     */
    protected SelectConditionStep<Record1<Integer>> selectCount(Consumer<F> filter) {
        return selectCount(getConditionByFilter(filter));
    }

    /**
     * 查询是否存在的辅助方法
     */
    protected SelectLimitPercentStep<Record1<Integer>> selectExist(Condition condition) {
        return getDslContext()
                .selectOne().from(getTable())
                .where(condition)
                .limit(1);
    }

    /**
     * 查询是否存在的辅助方法
     */
    protected SelectLimitPercentStep<Record1<Integer>> selectExist(Consumer<F> filter) {
        return selectExist(getConditionByFilter(filter));
    }

    @Override
    public K add(D data) {
        R record = dataToRecordForAdd(data);
        return getDslContext()
                .insertInto(getTable())
                .set(record)
                .returningResult(getPk())
                .fetchOne().value1();
    }

    @Override
    public int update(Consumer<U> updater, Consumer<F> filter) {
        U dataUpdater = getDataUpdater();
        if (dataUpdater instanceof NoopDataUpdater) {
            return 0;
        }
        updater.accept(dataUpdater);
        return getDslContext()
                .update(getTable())
                .set(((JooqRecordProvider<?>)dataUpdater).getRecord())
                .where(getConditionByFilter(filter))
                .execute();
    }

    @Override
    public int delete(Consumer<F> filter) {
        return getDslContext()
                .deleteFrom(getTable())
                .where(getConditionByFilter(filter))
                .execute();
    }

    @Override
    public Optional<D> getForWrite(Consumer<F> filter) {
        return select(filter)
                .limit(1)
                .forUpdate()
                .fetchOptional(this::recordToDataForQuery);
    }

    @Override
    public List<D> listForWrite(Consumer<F> filter) {
        return select(filter)
                .forUpdate()
                .fetch(this::recordToDataForQuery);
    }

    @Override
    public int countForWrite(Consumer<F> filter) {
        return selectCount(filter)
                .forUpdate()
                .fetchOne().value1();
    }

    @Override
    public boolean isExistedForWrite(Consumer<F> filter) {
        return selectExist(filter)
                .forUpdate()
                .fetchOptional().isPresent();
    }

    /**
     * 抽象的 JOOQ 查询器
     */
    protected abstract class AbstractJooqQuerier implements Querier<D, F>, JooqConditionHolder {
        @Getter(onMethod_ = @Override)
        @Setter(onMethod_ = @Override)
        private Condition condition = DSL.noCondition();
        @Getter(AccessLevel.PROTECTED)
        @Setter(AccessLevel.PROTECTED)
        private List<OrderField<?>> orderFields = Collections.emptyList();

        /**
         * 数据查询辅助方法
         */
        protected SelectSeekStepN<R> select() {
            return AbstractJooqRepository.this.select(getCondition())
                    .orderBy(getOrderFields());
        }

        @Override
        public void filter(Consumer<F> filter) {
            andCondition(getConditionByFilter(filter));
        }

        @Override
        public Optional<D> get() {
            return select().limit(1).fetchOptional(AbstractJooqRepository.this::recordToDataForQuery);
        }

        @Override
        public List<D> list() {
            return select().fetch(AbstractJooqRepository.this::recordToDataForQuery);
        }

        @Override
        public List<D> list(int offset, int limit) {
            return select().offset(offset).limit(limit).fetch(AbstractJooqRepository.this::recordToDataForQuery);
        }

        @Override
        public int count() {
            return AbstractJooqRepository.this.selectCount(getCondition()).fetchOne().value1();
        }

        @Override
        public boolean isExisted() {
            return AbstractJooqRepository.this.selectExist(getCondition()).fetchOptional().isPresent();
        }
    }
}
