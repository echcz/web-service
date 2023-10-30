package cn.echcz.webservice.exception;

/**
 * 认证异常
 */
public class AuthenticationException extends ClientException {

    public AuthenticationException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.ILLEGAL_ACCESS, message), cause);
    }

    public AuthenticationException(String message) {
        this(message, null);
    }

    public AuthenticationException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.ILLEGAL_ACCESS), cause);
    }

    public AuthenticationException() {
        this((Throwable) null);
    }
}
