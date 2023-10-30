package cn.echcz.webservice.entity;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 默认用户
 */
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUser implements User {
    @Getter
    @Setter
    private String tenantName;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private List<String> roles;

    public DefaultUser(String tenantName, String name) {
        this(tenantName, name, Collections.emptyList());
    }

    @Override
    public String toString() {
        return User.toString(this);
    }
}
