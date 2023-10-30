package cn.echcz.webservice.exception;

/**
 * 客户端错误
 */
public class ClientException extends ApplicationException {

    public ClientException(ErrorInfo errorInfo, String message, Throwable cause) {
        super(errorInfo, message, cause);
    }

    public ClientException(ErrorInfo errorInfo, String message) {
        super(errorInfo, message);
    }

    public ClientException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public ClientException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
