package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.adapter.repository.tables.records.ActionLogRecord;
import cn.echcz.webservice.entity.*;
import cn.echcz.webservice.usecase.repository.ActionLogRepository;
import cn.echcz.webservice.usecase.repository.NoopDataUpdater;
import cn.echcz.webservice.usecase.repository.QueryField;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static cn.echcz.webservice.adapter.repository.Tables.ACTION_LOG;

/**
 * 操作日志JOOQ仓库
 */
@Repository
public class JooqActionLogRepository
        extends AbstractJooqRepository<Long, ActionLog, ActionLogRecord,
        NoopDataUpdater, ActionLogRepository.ActionLogQueryFilter>
        implements ActionLogRepository {
    @Getter(value = AccessLevel.PROTECTED, onMethod_ = @Override)
    @Setter(onMethod_ = @Autowired)
    private DSLContext dslContext;

    @Override
    protected Table<ActionLogRecord> getTable() {
        return ACTION_LOG;
    }

    @Override
    protected TableField<ActionLogRecord, Long> getPk() {
        return ACTION_LOG.ID;
    }

    @Override
    protected ActionLog recordToDataForQuery(ActionLogRecord record) {
        return ActionLog.create(record.getId(), new ActionLogInitParamsAdapter(record));
    }

    @Override
    protected ActionLogRecord dataToRecordForAdd(ActionLog data) {
        ActionLogRecord record = new ActionLogRecord();
        record.setGroup(data.getGroup());
        record.setName(data.getName());
        record.setType(data.getType().name());
        record.setContent(data.getContent());
        User actor = data.getActor();
        record.setTenantName(actor.getTenantName());
        record.setActorName(actor.getName());
        record.setActorRoles(Joiner.on(",").join(actor.getRoles()));
        record.setActionTime(data.getActionTime());
        return record;
    }

    @Override
    protected NoopDataUpdater getDataUpdater() {
        return NoopDataUpdater.INSTANT;
    }

    @Override
    protected JooqActionLogQueryFilter getQueryFilter() {
        return new JooqActionLogQueryFilter();
    }

    @Override
    public ActionLogQuerier querier() {
        return new JooqActionLogQuerier();
    }

    /**
     * 操作日志查询过滤器实现
     */
    private static class JooqActionLogQueryFilter
            extends AbstractJooqQueryFilter<ActionLogQueryFilter>
            implements ActionLogQueryFilter {

        @Override
        protected JooqActionLogQueryFilter getSubQueryFilter() {
            return new JooqActionLogQueryFilter();
        }

        @Override
        public QueryField<String> tenantNameField() {
            return new JooqQueryField<>(this, ACTION_LOG.TENANT_NAME);
        }

        @Override
        public QueryField<String> groupField() {
            return new JooqQueryField<>(this, ACTION_LOG.GROUP);
        }

        @Override
        public QueryField<String> nameField() {
            return new JooqQueryField<>(this, ACTION_LOG.NAME);
        }

        @Override
        public QueryField<ActionType> typeField() {
            return new EnumJooqQueryField<>(this, ACTION_LOG.TYPE);
        }

        @Override
        public QueryField<String> actorNameField() {
            return new JooqQueryField<>(this, ACTION_LOG.ACTOR_NAME);
        }

        @Override
        public QueryField<LocalDateTime> actionTimeField() {
            return new JooqQueryField<>(this, ACTION_LOG.ACTION_TIME);
        }
    }

    /**
     * 操作日志查询器实现
     */
    private class JooqActionLogQuerier extends AbstractJooqQuerier implements ActionLogQuerier {
        @Override
        public void orderByActionTimeDesc() {
            setOrderFields(List.of(ACTION_LOG.ACTION_TIME.desc()));
        }
    }

    /**
     * 操作日志JOOQ记录类转 {@link ActionLogInitParams} 适配器
     */
    private static class ActionLogInitParamsAdapter implements ActionLogInitParams {
        private final ActionLogRecord record;

        public ActionLogInitParamsAdapter(ActionLogRecord record) {
            this.record = record;
        }

        @Override
        public String group() {
            return record.getGroup();
        }

        @Override
        public String name() {
            return record.getName();
        }

        @Override
        public ActionType type() {
            return ActionType.valueOf(record.getType());
        }

        @Override
        public String content() {
            return record.getContent();
        }

        @Override
        public User actor() {
            return new DefaultUser(record.getTenantName(), record.getActorName(),
                    Splitter.on(",").splitToList(record.getActorRoles()));
        }

        @Override
        public LocalDateTime actionTime() {
            return record.getActionTime();
        }
    }

}
