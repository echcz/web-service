/*
 * This file is generated by jOOQ.
 */
package cn.echcz.webservice.adapter.repository;


import cn.echcz.webservice.adapter.repository.tables.ActionLogTable;
import cn.echcz.webservice.adapter.repository.tables.DocumentTable;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * 操作日志
     */
    public final ActionLogTable ACTION_LOG = ActionLogTable.ACTION_LOG;

    /**
     * 文档
     */
    public final DocumentTable DOCUMENT = DocumentTable.DOCUMENT;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            ActionLogTable.ACTION_LOG,
            DocumentTable.DOCUMENT);
    }
}
