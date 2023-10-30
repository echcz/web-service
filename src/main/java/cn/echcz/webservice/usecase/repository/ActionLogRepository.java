package cn.echcz.webservice.usecase.repository;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.entity.ActionType;

import java.time.LocalDateTime;

import static cn.echcz.webservice.usecase.repository.ActionLogRepository.*;

/**
 * 操作日志仓库
 */
public interface ActionLogRepository
        extends BaseRepository<Long, ActionLog, NoopDataUpdater, ActionLogQueryFilter> {
    @Override
    ActionLogQuerier querier();

    /**
     * 操作日志查询过滤器
     */
    interface ActionLogQueryFilter extends QueryFilter<ActionLogQueryFilter> {
        QueryField<String> tenantNameField();

        QueryField<String> groupField();

        QueryField<String> nameField();

        QueryField<ActionType> typeField();

        QueryField<String> actorNameField();

        QueryField<LocalDateTime> actionTimeField();
    }

    /**
     * 操作日志查询器
     */
    interface ActionLogQuerier extends Querier<ActionLog, ActionLogQueryFilter> {
        /**
         * 按操作时间倒序排序
         */
        void orderByActionTimeDesc();
    }
}
