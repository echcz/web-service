package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.usecase.repository.QueryField;
import org.jooq.Field;

import java.util.Collection;

/**
 * JOOQ查询字段
 */
public class JooqQueryField<T> implements QueryField<T> {
    private final JooqConditionHolder conditionHolder;
    private final Field<T> field;

    public JooqQueryField(JooqConditionHolder conditionHolder, Field<T> field) {
        this.conditionHolder = conditionHolder;
        this.field = field;
    }

    @Override
    public void isNull() {
        conditionHolder.andCondition(field.isNull());
    }

    @Override
    public void notNull() {
        conditionHolder.andCondition(field.isNotNull());
    }

    @Override
    public void eq(T value) {
        conditionHolder.andCondition(field.eq(value));
    }

    @Override
    public void ne(T value) {
        conditionHolder.andCondition(field.ne(value));
    }

    @Override
    public void lt(T value) {
        conditionHolder.andCondition(field.lt(value));
    }

    @Override
    public void le(T value) {
        conditionHolder.andCondition(field.le(value));
    }

    @Override
    public void gt(T value) {
        conditionHolder.andCondition(field.gt(value));
    }

    @Override
    public void ge(T value) {
        conditionHolder.andCondition(field.ge(value));
    }

    @Override
    public void in(Collection<T> values) {
        conditionHolder.andCondition(field.in(values));
    }

    @Override
    public void notIn(Collection<T> values) {
        conditionHolder.andCondition(field.notIn(values));
    }

    @Override
    public void like(String pattern) {
        conditionHolder.andCondition(field.like(pattern));
    }

    @Override
    public void notLike(String pattern) {
        conditionHolder.andCondition(field.notLike(pattern));
    }
}
