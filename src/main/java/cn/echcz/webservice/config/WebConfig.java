package cn.echcz.webservice.config;

import cn.echcz.webservice.config.webfilter.AuthFilter;
import cn.echcz.webservice.config.webfilter.DataPermissionFilter;
import cn.echcz.webservice.config.webfilter.LogFilter;
import cn.echcz.webservice.config.webfilter.RequestInitFilter;
import cn.echcz.webservice.util.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web 配置
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Web Service REST API",
                description = "WEB 示例服务 REST API",
                version = "1.0"
        )
)
@SecuritySchemes({
        @SecurityScheme(
                type = SecuritySchemeType.HTTP,
                description = "Bearer凭证（JWT）",
                name = HttpHeaders.AUTHORIZATION,
                scheme = "bearer",
                bearerFormat = "JWT"
        ),
        @SecurityScheme(
                type = SecuritySchemeType.APIKEY,
                description = "数据权限头",
                name = Constants.HTTP_HEADER_DATA_PERMISSION,
                in = SecuritySchemeIn.HEADER
        )
})
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestInitFilter> requestInitFilterRegistration(RequestInitFilter requestInitFilter) {
        FilterRegistrationBean<RequestInitFilter> registrationBean = new FilterRegistrationBean<>(requestInitFilter);
        registrationBean.setUrlPatterns(List.of("/*"));
        registrationBean.setName("requestInitFilter");
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LogFilter> LogFilterRegistration(LogFilter logFilter) {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>(logFilter);
        registrationBean.setUrlPatterns(List.of("/*"));
        registrationBean.setName("logFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter authFilter) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(authFilter);
        registrationBean.setUrlPatterns(List.of("/*"));
        registrationBean.setName("authFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<DataPermissionFilter> dataPermissionFilterRegistration(DataPermissionFilter dataPermissionFilter) {
        FilterRegistrationBean<DataPermissionFilter> registrationBean = new FilterRegistrationBean<>(dataPermissionFilter);
        registrationBean.setUrlPatterns(List.of("/*"));
        registrationBean.setName("dataPermissionFilter");
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
