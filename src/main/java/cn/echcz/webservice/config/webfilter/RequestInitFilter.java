package cn.echcz.webservice.config.webfilter;

import com.google.common.base.Strings;
import cn.echcz.webservice.usecase.ContextProvider;
import cn.echcz.webservice.util.Constants;
import lombok.Setter;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 请求初始化过滤器，
 * 初始化上下文
 */
@Component
public class RequestInitFilter extends OncePerRequestFilter {
    @Setter(onMethod_ = @Autowired)
    private ContextProvider contextProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String transactionId = request.getHeader(Constants.HTTP_HEADER_TRANSACTION_ID);
        if (Strings.isNullOrEmpty(transactionId)) {
            transactionId = UUID.randomUUID().toString().replace("-", "");
        }
        try {
            contextProvider.getContext().setTransactionId(transactionId);
            MDC.put(Constants.SLF4J_MDC_KEY_TRANSACTION_ID, transactionId);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
            contextProvider.clear();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod());
    }
}
