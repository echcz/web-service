package cn.echcz.webservice.adapter.repository;

import org.jooq.Record;

/**
 * JOOQ记录提供器
 * @param <R> JOOQ记录类型
 */
public interface JooqRecordProvider<R extends Record> {
    /**
     * 获取JOOQ记录
     */
    R getRecord();
}
