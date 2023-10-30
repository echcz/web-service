package cn.echcz.webservice.entity;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 用户
 */
public interface User extends Principal {
    /**
     * 匿名用户
     */
    User ANONYMOUS_USER = new AnonymousUser();

    /**
     * 所属的租户名
     */
    String getTenantName();
    /**
     * 全名
     */
    default String getFullName() {
        return getName() + "@" + getTenantName();
    }
    /**
     * 获取用户角色
     */
    List<String> getRoles();

    /**
     * 是否是匿名用户
     */
    default boolean isAnonymous() {
        return false;
    }

    static String toString(User user) {
        if (Objects.isNull(user)) {
            return "null";
        }
        return user.getFullName()
                + "(anonymous=" + user.isAnonymous()
                + ", roles=" + user.getRoles()
                + ")";
    }

    /**
     * 匿名用户类
     */
    class AnonymousUser implements User {
        private AnonymousUser() {
            // Singleton pattern
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getTenantName() {
            return "";
        }

        @Override
        public String getFullName() {
            return "";
        }

        @Override
        public List<String> getRoles() {
            return Collections.emptyList();
        }

        @Override
        public boolean isAnonymous() {
            return true;
        }

        @Override
        public String toString() {
            return User.toString(this);
        }
    }
}
