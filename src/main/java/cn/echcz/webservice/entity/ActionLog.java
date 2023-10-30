package cn.echcz.webservice.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作日志
 */
public class ActionLog implements Entity<Long> {
    /**
     * ID
     */
    @Getter
    @Setter
    private Long id;
    /**
     * 操作组
     */
    @Getter
    @Setter
    private String group;
    /**
     * 操作名
     */
    @Getter
    @Setter
    private String name;
    /**
     * 操作类型
     */
    @Getter
    @Setter
    private ActionType type;
    /**
     * 操作类型
     */
    @Getter
    @Setter
    private String content;
    /**
     * 操作者
     */
    @Getter
    @Setter
    private User actor;
    /**
     * 操作时间
     */
    @Getter
    @Setter
    private LocalDateTime actionTime;

    public static ActionLog create(Long id, ActionLogInitParams params) {
        ActionLog r = new ActionLog();
        r.setId(id);
        r.setGroup(params.group());
        r.setName(params.name());
        r.setType(params.type());
        r.setContent(params.content());
        r.setActor(params.actor());
        r.setActionTime(params.actionTime());
        return r;
    }

    public static ActionLog create(ActionLogInitParams params) {
        return create(null, params);
    }
}
