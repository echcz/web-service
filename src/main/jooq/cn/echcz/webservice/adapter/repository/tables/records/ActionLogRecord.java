/*
 * This file is generated by jOOQ.
 */
package cn.echcz.webservice.adapter.repository.tables.records;


import cn.echcz.webservice.adapter.repository.tables.ActionLogTable;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 操作日志
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ActionLogRecord extends UpdatableRecordImpl<ActionLogRecord> implements Record9<Long, String, String, String, String, String, String, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>action_log.id</code>. 主键
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>action_log.id</code>. 主键
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>action_log.tenant_name</code>. 租户名
     */
    public void setTenantName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>action_log.tenant_name</code>. 租户名
     */
    public String getTenantName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>action_log.actor_name</code>. 操作者名
     */
    public void setActorName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>action_log.actor_name</code>. 操作者名
     */
    public String getActorName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>action_log.actor_roles</code>. 操作者拥有的角色，用逗号分隔
     */
    public void setActorRoles(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>action_log.actor_roles</code>. 操作者拥有的角色，用逗号分隔
     */
    public String getActorRoles() {
        return (String) get(3);
    }

    /**
     * Setter for <code>action_log.group</code>. 操作组
     */
    public void setGroup(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>action_log.group</code>. 操作组
     */
    public String getGroup() {
        return (String) get(4);
    }

    /**
     * Setter for <code>action_log.name</code>. 操作名
     */
    public void setName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>action_log.name</code>. 操作名
     */
    public String getName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>action_log.type</code>. 操作类型
     */
    public void setType(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>action_log.type</code>. 操作类型
     */
    public String getType() {
        return (String) get(6);
    }

    /**
     * Setter for <code>action_log.content</code>. 操作内容
     */
    public void setContent(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>action_log.content</code>. 操作内容
     */
    public String getContent() {
        return (String) get(7);
    }

    /**
     * Setter for <code>action_log.action_time</code>. 操作时间
     */
    public void setActionTime(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>action_log.action_time</code>. 操作时间
     */
    public LocalDateTime getActionTime() {
        return (LocalDateTime) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, String, String, String, String, LocalDateTime> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, String, String, String, String, String, String, String, LocalDateTime> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return ActionLogTable.ACTION_LOG.ID;
    }

    @Override
    public Field<String> field2() {
        return ActionLogTable.ACTION_LOG.TENANT_NAME;
    }

    @Override
    public Field<String> field3() {
        return ActionLogTable.ACTION_LOG.ACTOR_NAME;
    }

    @Override
    public Field<String> field4() {
        return ActionLogTable.ACTION_LOG.ACTOR_ROLES;
    }

    @Override
    public Field<String> field5() {
        return ActionLogTable.ACTION_LOG.GROUP;
    }

    @Override
    public Field<String> field6() {
        return ActionLogTable.ACTION_LOG.NAME;
    }

    @Override
    public Field<String> field7() {
        return ActionLogTable.ACTION_LOG.TYPE;
    }

    @Override
    public Field<String> field8() {
        return ActionLogTable.ACTION_LOG.CONTENT;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return ActionLogTable.ACTION_LOG.ACTION_TIME;
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
        return getActorName();
    }

    @Override
    public String component4() {
        return getActorRoles();
    }

    @Override
    public String component5() {
        return getGroup();
    }

    @Override
    public String component6() {
        return getName();
    }

    @Override
    public String component7() {
        return getType();
    }

    @Override
    public String component8() {
        return getContent();
    }

    @Override
    public LocalDateTime component9() {
        return getActionTime();
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
        return getActorName();
    }

    @Override
    public String value4() {
        return getActorRoles();
    }

    @Override
    public String value5() {
        return getGroup();
    }

    @Override
    public String value6() {
        return getName();
    }

    @Override
    public String value7() {
        return getType();
    }

    @Override
    public String value8() {
        return getContent();
    }

    @Override
    public LocalDateTime value9() {
        return getActionTime();
    }

    @Override
    public ActionLogRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ActionLogRecord value2(String value) {
        setTenantName(value);
        return this;
    }

    @Override
    public ActionLogRecord value3(String value) {
        setActorName(value);
        return this;
    }

    @Override
    public ActionLogRecord value4(String value) {
        setActorRoles(value);
        return this;
    }

    @Override
    public ActionLogRecord value5(String value) {
        setGroup(value);
        return this;
    }

    @Override
    public ActionLogRecord value6(String value) {
        setName(value);
        return this;
    }

    @Override
    public ActionLogRecord value7(String value) {
        setType(value);
        return this;
    }

    @Override
    public ActionLogRecord value8(String value) {
        setContent(value);
        return this;
    }

    @Override
    public ActionLogRecord value9(LocalDateTime value) {
        setActionTime(value);
        return this;
    }

    @Override
    public ActionLogRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, LocalDateTime value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActionLogRecord
     */
    public ActionLogRecord() {
        super(ActionLogTable.ACTION_LOG);
    }

    /**
     * Create a detached, initialised ActionLogRecord
     */
    public ActionLogRecord(Long id, String tenantName, String actorName, String actorRoles, String group, String name, String type, String content, LocalDateTime actionTime) {
        super(ActionLogTable.ACTION_LOG);

        setId(id);
        setTenantName(tenantName);
        setActorName(actorName);
        setActorRoles(actorRoles);
        setGroup(group);
        setName(name);
        setType(type);
        setContent(content);
        setActionTime(actionTime);
    }
}
