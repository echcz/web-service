/*
 * This file is generated by jOOQ.
 */
package cn.echcz.webservice.adapter.repository;


import cn.echcz.webservice.adapter.repository.tables.ActionLogTable;
import cn.echcz.webservice.adapter.repository.tables.DocumentTable;
import cn.echcz.webservice.adapter.repository.tables.records.ActionLogRecord;
import cn.echcz.webservice.adapter.repository.tables.records.DocumentRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * the default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ActionLogRecord> KEY_ACTION_LOG_PRIMARY = Internal.createUniqueKey(ActionLogTable.ACTION_LOG, DSL.name("KEY_action_log_PRIMARY"), new TableField[] { ActionLogTable.ACTION_LOG.ID }, true);
    public static final UniqueKey<DocumentRecord> KEY_DOCUMENT_PRIMARY = Internal.createUniqueKey(DocumentTable.DOCUMENT, DSL.name("KEY_document_PRIMARY"), new TableField[] { DocumentTable.DOCUMENT.ID }, true);
    public static final UniqueKey<DocumentRecord> KEY_DOCUMENT_UK_NAME = Internal.createUniqueKey(DocumentTable.DOCUMENT, DSL.name("KEY_document_uk_name"), new TableField[] { DocumentTable.DOCUMENT.TENANT_NAME, DocumentTable.DOCUMENT.NAME }, true);
}
