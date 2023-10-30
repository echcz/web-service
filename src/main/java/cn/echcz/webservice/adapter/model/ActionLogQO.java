package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.entity.ActionType;
import cn.echcz.webservice.usecase.repository.ActionLogRepository;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 操作日志查询对象
 */
@Schema(description = "操作日志查询对象", accessMode = Schema.AccessMode.WRITE_ONLY)
public record ActionLogQO(
        @Schema(description = "租户名称")
        String tenantName,
        @Schema(description = "操作组，模糊匹配")
        String group,
        @Schema(description = "操作名称，模糊匹配")
        String name,
        @Schema(description = "操作类型，模糊匹配")
        ActionType type,
        @Schema(description = "操作者名称")
        String actorName,
        @Schema(description = "操作时间最小值")
        LocalDateTime actionTimeMin,
        @Schema(description = "操作时间最大值")
        LocalDateTime actionTimeMax,
        @Schema(description = "查询偏移量")
        int offset,
        @Schema(description = "查询限制量")
        int limit
) {
    public PageVO<ActionLog> query(ActionLogRepository.ActionLogQuerier querier) {
        querier.filter(filter -> {
            if (!Strings.isNullOrEmpty(tenantName)) {
                filter.tenantNameField().eq(tenantName);
            }
            if (!Strings.isNullOrEmpty(group)) {
                filter.groupField().like("%" + group + "%");
            }
            if (!Strings.isNullOrEmpty(name)) {
                filter.nameField().like("%" + name + "%");
            }
            if (Objects.nonNull(type)) {
                filter.typeField().eq(type);
            }
            if (!Strings.isNullOrEmpty(actorName)) {
                filter.actorNameField().like("%" + actorName + "%");
            }
            if (Objects.nonNull(actionTimeMin)) {
                filter.actionTimeField().ge(actionTimeMin);
            }
            if (Objects.nonNull(actionTimeMax)) {
                filter.actionTimeField().le(actionTimeMax());
            }
        });
        int count = querier.count();
        if (count == 0) {
            return PageVO.empty();
        }
        querier.orderByActionTimeDesc();
        List<ActionLog> data = querier.list(offset, limit);
        return new PageVO<>(count, data);
    }
}
