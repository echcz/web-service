package cn.echcz.webservice.config.webfilter;

import cn.echcz.webservice.entity.DefaultUser;
import cn.echcz.webservice.entity.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Strings;
import cn.echcz.webservice.exception.AuthenticationException;
import cn.echcz.webservice.usecase.ContextProvider;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 认证过滤器，
 * 对请求进行认证
 */
@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Setter(onMethod_ = @Autowired)
    private ContextProvider contextProvider;

    public AuthFilter() {
        objectMapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .addModule(new JavaTimeModule())
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!token.startsWith("Bearer ")) {
            throw new AuthenticationException("不支持的凭证类型");
        }
        int i = token.indexOf('.');
        int j = token.indexOf('.', i + 1);
        if (i < 0 || j < 0) {
            log.warn("用户使用了非法的凭证: {}", token);
            throw new AuthenticationException("非法的凭证");
        }
        String userEnc = token.substring(i + 1, j);
        byte[] userRaw = Base64.getUrlDecoder().decode(userEnc.getBytes(StandardCharsets.US_ASCII));
        String userStr = new String(userRaw, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(userStr, DefaultUser.class);
        log.info("当前用户: {}", user);
        contextProvider.getContext().setCurrentUser(user);
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())
                || Strings.isNullOrEmpty(request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
