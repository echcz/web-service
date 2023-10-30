package cn.echcz.webservice.exception;

/**
 * 服务出错异常
 */
public class ServerFaultException extends ServerException {

    public ServerFaultException(String message, Throwable cause) {
        super(new ErrorInfo(ErrorCode.SERVER_FAULT, message), cause);
    }

    public ServerFaultException(String message) {
        this(message, null);
    }

    public ServerFaultException(Throwable cause) {
        super(new ErrorInfo(ErrorCode.SERVER_FAULT), cause);
    }

    public ServerFaultException() {
        this((Throwable) null);
    }
}
