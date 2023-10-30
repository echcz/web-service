package cn.echcz.webservice.adapter.controller;

import cn.echcz.webservice.entity.DefaultUser;
import cn.echcz.webservice.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.MapperFeature.USE_ANNOTATIONS;

/**
 * 测试API
 * 仅用于开发与测试
 * 不应在生产环境下启用
 */
@Tag(name = "Test", description = "仅用于开发与测试")
@ConditionalOnProperty(prefix = "test.api", name = "enabled")
@RestController
@RequestMapping("/test")
@Validated
@Slf4j
public class TestController {
    private final ObjectMapper objectMapper;

    public TestController() {
        objectMapper = JsonMapper.builder()
                .configure(USE_ANNOTATIONS, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
    }

    @Operation(summary = "生成" + HttpHeaders.AUTHORIZATION + "头的值")
    @GetMapping(value = "/token")
    public Map<String, ?> getToken (
            @Parameter(description = "租户名") @NotEmpty(message = "租户名(tenantName)不能为空") String tenantName,
            @Parameter(description = "用户名") @NotEmpty(message = "用户名(username)不能为空") String username,
            @Parameter(description = "角色，多个以逗号分隔") @RequestParam(defaultValue = "") List<String> roles
    ) throws JsonProcessingException {
        User user = new DefaultUser(tenantName, username, roles);
        String raw = objectMapper.writeValueAsString(user);
        String userEnc = Base64.getUrlEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8)).replace("=", "");
        return Map.of("value", "test." + userEnc + ".test");
    }
}
