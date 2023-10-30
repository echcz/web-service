package cn.echcz.webservice.entity;

/**
 * 用户动作/操作类型
 */
public enum ActionType {
    /**
     * 命令
     */
    COMMAND,
    /**
     * 读
     */
    READ,
    /**
     * 增
     */
    CREATE,
    /**
     * 改
     */
    UPDATE,
    /**
     * 删
     */
    DELETE,
    /**
     * 保存，upsert语义
     */
    SAVE,
    ;
}
