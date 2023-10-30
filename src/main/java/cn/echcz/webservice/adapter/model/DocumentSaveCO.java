package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.entity.DocumentInitParams;
import cn.echcz.webservice.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "文档保存命令对象", accessMode = Schema.AccessMode.WRITE_ONLY)
public record DocumentSaveCO(
        @Schema(description = "文档名称")
        @NotBlank(message = "文档名称不能为空")
        String name,
        @Schema(description = "文件地址")
        @NotBlank(message = "文件地址不能为空")
        String filePath
) {
    public DocumentInitParams toDocumentInitParams() {
        return new DocumentInitParamsAdapter();
    }

    private class DocumentInitParamsAdapter implements DocumentInitParams {

        @Override
        public String name() {
            return name;
        }

        @Override
        public String filePath() {
            return filePath;
        }

        @Override
        public User user() {
            return null;
        }

        @Override
        public LocalDateTime createTime() {
            return null;
        }

        @Override
        public LocalDateTime updateTime() {
            return null;
        }
    }
}
