package cn.echcz.webservice.exception;

/**
 * 服务端错误
 */
public class ServerException extends ApplicationException {

    public ServerException(ErrorInfo errorInfo, String message, Throwable cause) {
        super(errorInfo, message, cause);
    }

    public ServerException(ErrorInfo errorInfo, String message) {
        super(errorInfo, message);
    }

    public ServerException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public ServerException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
