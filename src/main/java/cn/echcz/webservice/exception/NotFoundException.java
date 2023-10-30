package cn.echcz.webservice.exception;

/**
 * 数据不存在异常
 */
public class NotFoundException extends ClientException {

    public NotFoundException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.NOT_FOUND, message), cause);
    }

    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.NOT_FOUND), cause);
    }

    public NotFoundException() {
        this((Throwable) null);
    }
}
