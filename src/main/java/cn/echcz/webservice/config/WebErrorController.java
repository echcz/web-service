package cn.echcz.webservice.config;

import cn.echcz.webservice.exception.ErrorInfo;
import cn.echcz.webservice.exception.ErrorCode;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class WebErrorController implements ErrorController {
    @Setter(onMethod_ = @Autowired)
    private ErrorAttributes errorAttributes;

    @RequestMapping
    public ResponseEntity<ErrorInfo> error(WebRequest request) throws Throwable {
        Throwable error = errorAttributes.getError(request);
        if (Objects.nonNull(error)) {
            throw error;
        }
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        Integer status = (Integer) attributes.getOrDefault("status", 500);
        String message = attributes.getOrDefault("error", "Unknown").toString();
        ErrorCode errorCode;
        if (status >= 500) {
            errorCode = ErrorCode.SERVER_FAULT;
        } else {
            errorCode = ErrorCode.BAD_REQUEST;
        }
        return new ResponseEntity<>(new ErrorInfo(errorCode, message), HttpStatus.valueOf(status));
    }
}
