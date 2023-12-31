/*
 * This file is generated by jOOQ.
 */
package cn.echcz.webservice.adapter.repository;


import cn.echcz.webservice.adapter.repository.tables.ActionLogTable;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in the default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACTION_LOG_IDX_TENANT_ACTION_TIME = Internal.createIndex(DSL.name("idx_tenant_action_time"), ActionLogTable.ACTION_LOG, new OrderField[] { ActionLogTable.ACTION_LOG.TENANT_NAME, ActionLogTable.ACTION_LOG.ACTION_TIME }, false);
}
