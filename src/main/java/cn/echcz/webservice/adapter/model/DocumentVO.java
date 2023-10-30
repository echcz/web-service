package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.entity.Document;
import cn.echcz.webservice.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.function.Function;

@Schema(description = "文档显示对象", accessMode = Schema.AccessMode.READ_ONLY)
public record DocumentVO(
        @Schema(description = "文档ID")
        Long id,
        @Schema(description = "文档名称")
        String name,
        @Schema(description = "文件地址")
        String filePath,
        @Schema(description = "文件下载URL")
        String fileDownloadUrl,
        @Schema(description = "所属租户名")
        String tenantName,
        @Schema(description = "所属用户名")
        String username,
        @Schema(description = "创建时间")
        LocalDateTime createTime,
        @Schema(description = "更新时间")
        LocalDateTime updateTime
) {
    public static DocumentVO fromEntity(Document entity, Function<Document, String> fileDownloadUrlGetter) {
        User user = entity.getUser();
        return new DocumentVO(
                entity.getId(),
                entity.getName(),
                entity.getFilePath(),
                fileDownloadUrlGetter.apply(entity),
                user.getTenantName(),
                user.getName(),
                entity.getCreateTime(),
                entity.getUpdateTime()
        );
    }
}
