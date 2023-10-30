package cn.echcz.webservice.adapter.repository;

import org.jooq.Condition;

/**
 * Jooq查询条件持有者接口，
 * 用于暴露其读取并设置其Jooq查询条件的接口
 */
public interface JooqConditionHolder {
    /**
     * 获取Jooq查询条件
     */
    Condition getCondition();

    /**
     * 设置Jooq查询条件
     */
    void setCondition(Condition condition);

    /**
     * 将查询条件设置为其查询条件 与 提供的查询条件
     */
    default void andCondition(Condition condition) {
        setCondition(getCondition().and(condition));
    }

    /**
     * 将查询条件设置为其查询条件 或 提供的查询条件
     */
    default void orCondition(Condition condition) {
        setCondition(getCondition().or(condition));
    }

    /**
     * 将查询条件设置为其查询条件取非
     */
    default void notCondition() {
        setCondition(getCondition().not());
    }
}
