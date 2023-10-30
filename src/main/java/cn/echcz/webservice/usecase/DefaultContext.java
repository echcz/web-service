package cn.echcz.webservice.usecase;

import cn.echcz.webservice.entity.User;
import com.google.common.base.Preconditions;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 默认上下文
 */
public class DefaultContext implements Context {
    @Getter
    private String transactionId;
    @Getter
    private User currentUser;
    @Getter
    private List<String> dataPermissions;

    public DefaultContext(String transactionId, User currentUser, List<String> dataPermissions) {
        this.setTransactionId(transactionId);
        this.setCurrentUser(currentUser);
        this.setDataPermissions(dataPermissions);
    }

    public DefaultContext(String transactionId, User currentUser) {
        this(transactionId, currentUser, Collections.emptyList());
    }

    public DefaultContext() {
        this("", User.ANONYMOUS_USER, Collections.emptyList());
    }

    @Override
    public void setTransactionId(String transactionId) {
        Preconditions.checkNotNull(transactionId);
        this.transactionId = transactionId;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        Preconditions.checkNotNull(currentUser);
        this.currentUser = currentUser;
    }

    @Override
    public void setDataPermissions(List<String> dataPermissions) {
        if (Objects.isNull(dataPermissions)) {
            dataPermissions = Collections.emptyList();
        }
        this.dataPermissions = dataPermissions;
    }
}
