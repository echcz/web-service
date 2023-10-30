package cn.echcz.webservice.exception;

import lombok.Getter;

/**
 * 错误码
 */
public enum ErrorCode {
    SERVER_FAULT("服务异常"),
    SERVER_INCOMPLETE("服务未完成"),
    BAD_REQUEST("错误的请求"),
    UNSUPPORTED("不支持的操作"),
    ILLEGAL_ACCESS("非法访问"),
    ILLEGAL_ARGUMENT("非法的参数"),
    NOT_FOUND("数据不存在"),
    DATA_DUPLICATE("数据已存在"),
    /* 请根据自己的实际请问添加更多状态码 */
    ;

    /**
     * 错误消息
     */
    @Getter
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}