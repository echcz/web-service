package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.usecase.repository.QueryField;
import org.jooq.Field;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 可进行类型映射/转换的JOOQ查询字段
 *
 * @param <T> 目标（程序内存中的）类型
 * @param <R> 原始（数据库中的）类型
 */
public class MappingJooqQueryField<T, R> implements QueryField<T> {
    private final JooqQueryField<R> queryField;
    private final Function<T, R> mapper;

    public MappingJooqQueryField(JooqConditionHolder conditionHolder, Field<R> field, Function<T, R> mapper) {
        this.queryField = new JooqQueryField<>(conditionHolder, field);
        this.mapper = mapper;
    }

    @Override
    public void isNull() {
        queryField.isNull();
    }

    @Override
    public void notNull() {
        queryField.notNull();
    }

    @Override
    public void eq(T value) {
        queryField.eq(mapper.apply(value));
    }

    @Override
    public void ne(T value) {
        queryField.ne(mapper.apply(value));
    }

    @Override
    public void lt(T value) {
        queryField.lt(mapper.apply(value));
    }

    @Override
    public void le(T value) {
        queryField.le(mapper.apply(value));
    }

    @Override
    public void gt(T value) {
        queryField.gt(mapper.apply(value));
    }

    @Override
    public void ge(T value) {
        queryField.ge(mapper.apply(value));
    }

    @Override
    public void in(Collection<T> values) {
        List<R> vs = values.stream().map(mapper).toList();
        queryField.in(vs);
    }

    @Override
    public void notIn(Collection<T> values) {
        List<R> vs = values.stream().map(mapper).toList();
        queryField.notIn(vs);
    }

    @Override
    public void like(String pattern) {
        queryField.like(pattern);
    }

    @Override
    public void notLike(String pattern) {
        queryField.notLike(pattern);
    }
}
