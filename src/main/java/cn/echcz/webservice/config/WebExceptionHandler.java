package cn.echcz.webservice.config;

import cn.echcz.webservice.exception.AuthorizationException;
import cn.echcz.webservice.exception.ClientException;
import cn.echcz.webservice.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cn.echcz.webservice.exception.ApplicationException;
import cn.echcz.webservice.exception.AuthenticationException;
import cn.echcz.webservice.exception.ErrorCode;
import cn.echcz.webservice.exception.ServerException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> handleAuthenticationException(AuthenticationException e) {
        log.info("用户认证失败: {}", e.toString());
        return new ResponseEntity<>(e.getErrorInfo(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorInfo> handleAuthorizationException(AuthorizationException e) {
        log.info("用户授权失败: {}", e.toString());
        return new ResponseEntity<>(e.getErrorInfo(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorInfo> handleClientException(ClientException e) {
        log.info("用户请求错误: {}", e.toString());
        return new ResponseEntity<>(e.getErrorInfo(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorInfo> handleServerException(ServerException e) {
        log.info("服务处理错误: {}", e.toString());
        return new ResponseEntity<>(e.getErrorInfo(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> handleApplicationException(ApplicationException e) {
        log.info("应用错误: {}", e.toString());
        return new ResponseEntity<>(e.getErrorInfo(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> handleConstraintViolationException(ConstraintViolationException e) {
        log.info("用户请求错误: {}", e.toString());
        List<String> details = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.ILLEGAL_ARGUMENT, ErrorCode.ILLEGAL_ARGUMENT.getMessage(), details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> handleBindException(BindException e) {
        log.info("用户请求错误: {}", e.toString());
        List<String> details = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).filter(Objects::nonNull).toList();
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.ILLEGAL_ARGUMENT, ErrorCode.ILLEGAL_ARGUMENT.getMessage(), details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorInfo> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("用户请求错误: {}", e.toString());
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.UNSUPPORTED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorInfo> handleMediaTypeException(HttpMediaTypeException e) {
        log.info("用户请求错误: {}", e.toString());
        HttpStatus status;
        if (e instanceof HttpMediaTypeNotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        }
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.UNSUPPORTED), status);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, ServletRequestBindingException.class })
    public ResponseEntity<ErrorInfo> handleServletException(Exception e) {
        log.info("用户请求错误: {}", e.toString());
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleException(Exception e) {
        log.error("服务异常", e);
        return new ResponseEntity<>(new ErrorInfo(ErrorCode.SERVER_FAULT), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
