package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.usecase.repository.DataUpdater;
import lombok.Getter;
import org.jooq.Record;

/**
 * Jooq数据更新器
 */
public abstract class AbstractJooqDataUpdater<R extends Record>
        implements DataUpdater, JooqRecordProvider<R> {
    @Getter(onMethod_ = @Override)
    private final R record;

    public AbstractJooqDataUpdater(R record) {
        this.record = record;
    }
}
