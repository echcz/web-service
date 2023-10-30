package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.usecase.repository.QueryFilter;
import lombok.Getter;
import lombok.Setter;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.function.Consumer;

/**
 * 抽象的Jooq查询过滤器
 *
 * @param <T> 子查询过滤器
 */
public abstract class AbstractJooqQueryFilter<T extends QueryFilter<?>>
        implements QueryFilter<T>, JooqConditionHolder {
    @Getter(onMethod_ = @Override)
    @Setter(onMethod_ = @Override)
    private Condition condition = DSL.noCondition();

    /**
     * 获取子查询过滤器
     * <br/>NOTE: 应该每次获取/创建一个新的查询过滤器
     * <br/>IMPORTANT: 必须实现 {@link JooqConditionHolder} 接口
     */
    protected abstract T getSubQueryFilter();

    @Override
    public void and(Consumer<T> filter) {
        T subQueryFilter = getSubQueryFilter();
        filter.accept(subQueryFilter);
        andCondition(((JooqConditionHolder) subQueryFilter).getCondition());
    }

    @Override
    public void or(Consumer<T> filter) {
        T subQueryFilter = getSubQueryFilter();
        filter.accept(subQueryFilter);
        orCondition(((JooqConditionHolder) subQueryFilter).getCondition());
    }

    @Override
    public void not() {
        notCondition();
    }
}
