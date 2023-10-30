package cn.echcz.webservice.exception;

/**
 * 请求有误异常
 */
public class BadRequestException extends ClientException {

    public BadRequestException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.BAD_REQUEST, message), cause);
    }

    public BadRequestException(String message) {
        this(message, null);
    }

    public BadRequestException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.BAD_REQUEST), cause);
    }

    public BadRequestException() {
        this((Throwable) null);
    }
}
