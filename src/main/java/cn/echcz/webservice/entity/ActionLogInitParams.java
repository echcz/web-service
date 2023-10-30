package cn.echcz.webservice.entity;

import java.time.LocalDateTime;

/**
 * 操作日志初始化参数，辅助创建操作日志实体
 */
public interface ActionLogInitParams {
    String group();
    String name();
    ActionType type();
    String content();
    User actor();
    LocalDateTime actionTime();
}
