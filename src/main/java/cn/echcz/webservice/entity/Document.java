package cn.echcz.webservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.tools.DocumentationTool;
import java.time.LocalDateTime;

/**
 * 文档
 */
public class Document implements Entity<Long> {
    /**
     * ID
     */
    @Getter
    @Setter
    private Long id;
    /**
     * 文档名
     */
    @Getter
    @Setter
    private String name;
    /**
     * 文件地址
     */
    @Getter
    @Setter
    private String filePath;
    /**
     * 文档所有者
     */
    @Getter
    @Setter
    private User user;
    /**
     * 创建时间
     */
    @Getter
    @Setter
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Getter
    @Setter
    private LocalDateTime updateTime;

    public static Document create(Long id, DocumentInitParams params) {
        Document r = new Document();
        r.setId(id);
        r.setName(params.name());
        r.setFilePath(params.filePath());
        r.setUser(params.user());
        r.setCreateTime(params.createTime());
        r.setUpdateTime(params.updateTime());
        return r;
    }

    public static Document create(DocumentInitParams params) {
        return create(null, params);
    }

}
