package cn.echcz.webservice.exception;

/**
 * 数据已存在异常
 */
public class DataDuplicateException extends ClientException {

    public DataDuplicateException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.DATA_DUPLICATE, message), cause);
    }

    public DataDuplicateException(String message) {
        this(message, null);
    }

    public DataDuplicateException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.DATA_DUPLICATE), cause);
    }

    public DataDuplicateException() {
        this((Throwable) null);
    }
}
