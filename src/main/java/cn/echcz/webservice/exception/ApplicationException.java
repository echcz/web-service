package cn.echcz.webservice.exception;

import lombok.Getter;

/**
 * 应用错误
 */
public class ApplicationException extends RuntimeException {
    /**
     * 错误数据体
     */
    @Getter
    private final ErrorInfo errorInfo;

    public ApplicationException(ErrorInfo errorInfo, String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = errorInfo;
    }

    public ApplicationException(ErrorInfo errorInfo, String message) {
        this(errorInfo, message, null);
    }

    public ApplicationException(ErrorInfo errorInfo, Throwable cause) {
        this(errorInfo, errorInfo.code().name() + ": " + errorInfo.message(), cause);
    }

    public ApplicationException(ErrorInfo errorInfo) {
        this(errorInfo, (Throwable) null);
    }
}
