package cn.echcz.webservice.usecase;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.echcz.webservice.entity.User;
import com.google.common.collect.Iterables;
import cn.echcz.webservice.exception.AuthenticationException;

/**
 * 应用上下文
 */
public interface Context {
    /**
     * 设置事务ID
     *
     * @param transactionId 当前事务的唯一标识，如果事务是跨服务的，则应该将此ID进行跨服务传播
     */
    void setTransactionId(String transactionId);

    /**
     * 获取事务ID
     */
    String getTransactionId();

    /**
     * 设置当前用户
     *
     * @param user
     */
    void setCurrentUser(User user);

    /**
     * 获取当前用户
     */
    default User getCurrentUser() {
        return User.ANONYMOUS_USER;
    }

    /**
     * 设置当前请求的数据权限
     */
    void setDataPermissions(List<String> dataPermissions);

    /**
     * 获取当前请求的数据权限
     */
    default List<String> getDataPermissions() {
        return Collections.emptyList();
    }

    /**
     * 当前用户是否有所有指定角色
     *
     * @param roles 指定角色
     */
    default boolean hasAllRoles(Collection<String> roles) {
        if (roles.isEmpty()) {
            return true;
        }
        List<String> currentRoles = getCurrentUser().getRoles();
        return Iterables.all(roles, currentRoles::contains);
    }

    /**
     * 当前用户需要拥有所有指定角色
     *
     * @throws AuthenticationException 如果用户没有指定的全部角色
     */
    default void needAllRoles(Collection<String> roles) {
        if (!hasAllRoles(roles)) {
            throw new AuthenticationException("无访问权限");
        }
    }

    /**
     * 当前用户是否有任意指定角色
     *
     * @param roles 指定角色
     */
    default boolean hasAnyRoles(Collection<String> roles) {
        if (roles.isEmpty()) {
            return true;
        }
        List<String> currentRoles = getCurrentUser().getRoles();
        return Iterables.any(roles, currentRoles::contains);
    }

    /**
     * 当前用户需要拥有任意指定角色
     *
     * @throws AuthenticationException 如果用户没有指定的任意角色
     */
    default void needAnyRoles(Collection<String> roles) throws AuthenticationException {
        if (!hasAnyRoles(roles)) {
            throw new AuthenticationException("无访问权限");
        }
    }

    /**
     * 检查是否是匿名访问
     */
    default boolean isAnonymous() {
        return getCurrentUser().isAnonymous();
    }

    /**
     * 必须不是匿名访问
     *
     * @throws AuthenticationException 如果是匿名访问
     */
    default void mustNotAnonymous() {
        if (isAnonymous()) {
            throw new AuthenticationException("无访问权限");
        }
    }

    /**
     * 当前请求是否有指定的数据权限
     */
    default boolean hasDataPermission(String dataPermission) {
        return getDataPermissions().contains(dataPermission);
    }
}
