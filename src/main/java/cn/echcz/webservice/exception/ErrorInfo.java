package cn.echcz.webservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误信息
 *
 * @param code    错误码
 * @param message 错误消息
 * @param details 错误详情
 */
@Schema(description = "错误信息", accessMode = Schema.AccessMode.READ_ONLY)
public record ErrorInfo(
        @Schema(description = "错误码")
        ErrorCode code,
        @Schema(description = "错误消息")
        String message,
        @Schema(description = "错误详情")
        Object details
) {
    public ErrorInfo(ErrorCode code) {
        this(code, code.getMessage(), null);
    }

    public ErrorInfo(ErrorCode code, String message) {
        this(code, message, null);
    }

}
