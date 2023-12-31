/*
 * This file is generated by jOOQ.
 */
package cn.echcz.webservice.adapter.repository.tables.records;


import cn.echcz.webservice.adapter.repository.tables.DocumentTable;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 文档
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DocumentRecord extends UpdatableRecordImpl<DocumentRecord> implements Record7<Long, String, String, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>document.id</code>. 主键
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>document.id</code>. 主键
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>document.tenant_name</code>. 租户名
     */
    public void setTenantName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>document.tenant_name</code>. 租户名
     */
    public String getTenantName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>document.name</code>. 文档名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>document.name</code>. 文档名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>document.file_path</code>. 文件地址
     */
    public void setFilePath(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>document.file_path</code>. 文件地址
     */
    public String getFilePath() {
        return (String) get(3);
    }

    /**
     * Setter for <code>document.username</code>. 用户名
     */
    public void setUsername(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>document.username</code>. 用户名
     */
    public String getUsername() {
        return (String) get(4);
    }

    /**
     * Setter for <code>document.create_time</code>. 创建时间
     */
    public void setCreateTime(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>document.create_time</code>. 创建时间
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>document.update_time</code>. 更新时间
     */
    public void setUpdateTime(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>document.update_time</code>. 更新时间
     */
    public LocalDateTime getUpdateTime() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, String, String, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return DocumentTable.DOCUMENT.ID;
    }

    @Override
    public Field<String> field2() {
        return DocumentTable.DOCUMENT.TENANT_NAME;
    }

    @Override
    public Field<String> field3() {
        return DocumentTable.DOCUMENT.NAME;
    }

    @Override
    public Field<String> field4() {
        return DocumentTable.DOCUMENT.FILE_PATH;
    }

    @Override
    public Field<String> field5() {
        return DocumentTable.DOCUMENT.USERNAME;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return DocumentTable.DOCUMENT.CREATE_TIME;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return DocumentTable.DOCUMENT.UPDATE_TIME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getTenantName();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getFilePath();
    }

    @Override
    public String component5() {
        return getUsername();
    }

    @Override
    public LocalDateTime component6() {
        return getCreateTime();
    }

    @Override
    public LocalDateTime component7() {
        return getUpdateTime();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getTenantName();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getFilePath();
    }

    @Override
    public String value5() {
        return getUsername();
    }

    @Override
    public LocalDateTime value6() {
        return getCreateTime();
    }

    @Override
    public LocalDateTime value7() {
        return getUpdateTime();
    }

    @Override
    public DocumentRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public DocumentRecord value2(String value) {
        setTenantName(value);
        return this;
    }

    @Override
    public DocumentRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public DocumentRecord value4(String value) {
        setFilePath(value);
        return this;
    }

    @Override
    public DocumentRecord value5(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public DocumentRecord value6(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public DocumentRecord value7(LocalDateTime value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public DocumentRecord values(Long value1, String value2, String value3, String value4, String value5, LocalDateTime value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DocumentRecord
     */
    public DocumentRecord() {
        super(DocumentTable.DOCUMENT);
    }

    /**
     * Create a detached, initialised DocumentRecord
     */
    public DocumentRecord(Long id, String tenantName, String name, String filePath, String username, LocalDateTime createTime, LocalDateTime updateTime) {
        super(DocumentTable.DOCUMENT);

        setId(id);
        setTenantName(tenantName);
        setName(name);
        setFilePath(filePath);
        setUsername(username);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
    }
}
