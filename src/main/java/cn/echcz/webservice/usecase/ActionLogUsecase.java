package cn.echcz.webservice.usecase;

import cn.echcz.webservice.entity.ActionLog;
import cn.echcz.webservice.entity.User;
import cn.echcz.webservice.usecase.repository.ActionLogRepository;
import cn.echcz.webservice.util.Constants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志用例
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ActionLogUsecase extends AbstractUsecase {
    @Setter(onMethod_ = @Autowired)
    @Getter(value = AccessLevel.PROTECTED, onMethod_ = @Override)
    private ContextProvider contextProvider;

    @Setter(onMethod_ = @Autowired)
    @Getter(AccessLevel.PROTECTED)
    private ActionLogRepository repository;

    /**
     * 记录操作日志
     */
    public void logging(ActionLog actionLog) {
        getRepository().add(actionLog);
    }

    /**
     * 以当前用户为操作者记录操作日志
     */
    public void loggingWithCurrentUser(ActionLog actionLog) {
        actionLog.setActor(context().getCurrentUser());
        logging(actionLog);
    }

    /**
     * 根据上下文进行过滤
     * @param filter 过滤器
     */
    protected void filterByContext(ActionLogRepository.ActionLogQueryFilter filter) {
        Context context = context();
        if (context.hasDataPermission(Constants.DATA_PERMISSION_ALL)) {
            return;
        }
        User user = context.getCurrentUser();
        if (context.hasDataPermission(Constants.DATA_PERMISSION_TENANT)) {
            filter.tenantNameField().eq(user.getTenantName());
        } else {
            filter.tenantNameField().eq(user.getTenantName());
            filter.actorNameField().eq(user.getName());
        }
    }

    /**
     * 生成操作日志查询器以让调用者自定义查询操作日志
     */
    public ActionLogRepository.ActionLogQuerier querier() {
        ActionLogRepository.ActionLogQuerier querier = getRepository().querier();
        querier.filter(this::filterByContext);
        return querier;
    }
}
