package cn.echcz.webservice.adapter.repository;

import org.jooq.Field;

/**
 * 枚举JOOQ查询字段
 */
public class EnumJooqQueryField<T extends Enum<T>> extends MappingJooqQueryField<T, String> {
    public EnumJooqQueryField(JooqConditionHolder conditionHolder, Field<String> field) {
        super(conditionHolder, field, Enum::name);
    }
}
