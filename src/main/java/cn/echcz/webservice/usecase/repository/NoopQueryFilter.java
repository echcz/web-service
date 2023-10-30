package cn.echcz.webservice.usecase.repository;

import java.util.function.Consumer;

/**
 * 不进行过滤的查询过滤器，
 * 标记接口
 */
public final class NoopQueryFilter implements QueryFilter<QueryFilter<?>> {
    public static final NoopQueryFilter INSTANT = new NoopQueryFilter();

    private NoopQueryFilter() {
        // Singleton
    }

    @Override
    public void and(Consumer<QueryFilter<?>> filter) {
        // Do nothing
    }

    @Override
    public void or(Consumer<QueryFilter<?>> filter) {
        // Do nothing
    }

    @Override
    public void not() {
        // Do nothing
    }
}
