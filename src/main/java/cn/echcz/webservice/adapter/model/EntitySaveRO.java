package cn.echcz.webservice.adapter.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * 实体保存结果对象
 */
@Schema(description = "实体保存（添加或更新）响应对象", accessMode = Schema.AccessMode.READ_ONLY)
public record EntitySaveRO(
        @Schema(description = "是否成功保存")
        boolean succeeded,
        @Schema(description = "实体ID")
        Long id
) {
    public EntitySaveRO(Long id) {
        this(Objects.nonNull(id), id);
    }
}
