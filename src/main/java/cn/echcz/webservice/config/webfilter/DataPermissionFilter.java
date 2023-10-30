package cn.echcz.webservice.config.webfilter;

import com.google.common.base.Splitter;
import cn.echcz.webservice.usecase.ContextProvider;
import cn.echcz.webservice.util.Constants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 数据权限过滤器，
 * 获取当前请求的数据权限
 */
@Component
@Slf4j
public class DataPermissionFilter extends OncePerRequestFilter {
    @Setter(onMethod_ = @Autowired)
    private ContextProvider contextProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String dataPermissionStr = request.getHeader(Constants.HTTP_HEADER_DATA_PERMISSION);
        List<String> dataPermissions;
        if (dataPermissionStr.isBlank()) {
            dataPermissions = Collections.emptyList();
        } else {
            dataPermissions = Splitter.on(",").splitToList(dataPermissionStr);
        }
        log.info("当前数据权限: {}", dataPermissions);
        contextProvider.getContext().setDataPermissions(dataPermissions);
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())
                || Objects.isNull(request.getHeader(Constants.HTTP_HEADER_DATA_PERMISSION));
    }
}
