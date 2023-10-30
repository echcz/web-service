package cn.echcz.webservice.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 数据删除结果对象
 */
@Schema(description = "数据删除结果对象", accessMode = Schema.AccessMode.READ_ONLY)
public record DeleteRO(
        @Schema(description = "删除的数据")
        int count
) {
}
