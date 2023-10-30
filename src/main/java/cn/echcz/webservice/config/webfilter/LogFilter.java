package cn.echcz.webservice.config.webfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import com.google.common.collect.Iterables;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 日志过滤器，
 * 在HTTP请求与响应时记录日志
 */
@Component
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    private final List<PathPattern> ignorePaths;

    public LogFilter(@Value("${logging.webfilter.ignore.paths:/,/health/**}") List<String> ignorePaths) {
        this.ignorePaths = ignorePaths.stream().map(PathPatternParser.defaultInstance::parse).toList();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        long st = System.currentTimeMillis();
        String path = request.getServletPath();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        if (Objects.isNull(queryString)) {
            queryString = "";
        } else {
            queryString = "?" + queryString;
        }
        log.info("Http Request: {} {}{}", method, path, queryString);
        chain.doFilter(request, response);
        int status = response.getStatus();
        long t = System.currentTimeMillis() - st;
        log.info("Http Response: {}({}ms)", status, t);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        PathContainer pathContainer = PathContainer.parsePath(path);
        return Iterables.any(ignorePaths, v -> v.matches(pathContainer));
    }
}
