package cn.echcz.webservice.entity;

import java.time.LocalDateTime;

/**
 * 文档初始化参数，辅助创建文档实体
 */
public interface DocumentInitParams {
    String name();
    String filePath();
    User user();
    LocalDateTime createTime();
    LocalDateTime updateTime();
}
