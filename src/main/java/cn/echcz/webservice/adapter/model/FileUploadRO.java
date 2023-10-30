package cn.echcz.webservice.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 上传文件结果对象
 */
@Schema(description = "上传文件返回对象", accessMode = Schema.AccessMode.READ_ONLY)
public record FileUploadRO(
        @Schema(description = "文件地址")
        String path
) {
}
