package cn.echcz.webservice.exception;

/**
 * 授权异常
 */
public class AuthorizationException extends ClientException {

    public AuthorizationException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.ILLEGAL_ACCESS, message), cause);
    }

    public AuthorizationException(String message) {
        this(message, null);
    }

    public AuthorizationException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.ILLEGAL_ACCESS), cause);
    }

    public AuthorizationException() {
        this((Throwable) null);
    }
}
