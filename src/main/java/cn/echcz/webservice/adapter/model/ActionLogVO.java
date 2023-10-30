package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.entity.ActionType;
import cn.echcz.webservice.entity.User;
import com.google.common.base.Joiner;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 操作日志显示对象
 */
@Schema(description = "操作日志显示对象", accessMode = Schema.AccessMode.READ_ONLY)
public record ActionLogVO(
        @Schema(description = "操作日志ID")
        Long id,
        @Schema(description = "操作分组")
        String group,
        @Schema(description = "操作名")
        String name,
        @Schema(description = "操作类型")
        ActionType type,
        @Schema(description = "操作内容")
        String content,
        @Schema(description = "用户名")
        String actorName,
        @Schema(description = "用户角色")
        String actorRoles,
        @Schema(description = "操作时间")
        LocalDateTime actionTime
) {
    public static ActionLogVO fromEntity(ActionLog entity) {
        User actor = entity.getActor();
        String actorName = actor.getName();
        String actorRoles = Joiner.on(",").join(actor.getRoles());
        return new ActionLogVO(
                entity.getId(),
                entity.getGroup(),
                entity.getName(),
                entity.getType(),
                entity.getContent(),
                actorName,
                actorRoles,
                entity.getActionTime()
        );
    }
}
