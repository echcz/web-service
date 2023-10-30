package cn.echcz.webservice.usecase;

import cn.echcz.webservice.entity.Document;
import cn.echcz.webservice.entity.User;
import cn.echcz.webservice.usecase.repository.DocumentRepository;
import cn.echcz.webservice.util.Constants;
import cn.echcz.webservice.exception.DataDuplicateException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * 文档用例
 */
@Service
@Transactional
public class DocumentUsecase extends AbstractEntityUsecase<Long, Document,
        DocumentRepository.DocumentUpdater, DocumentRepository.DocumentQueryFilter, DocumentRepository> {
    @Setter(onMethod_ = @Autowired)
    @Getter(AccessLevel.PROTECTED)
    private ContextProvider contextProvider;

    @Setter(onMethod_ = @Autowired)
    @Getter(AccessLevel.PROTECTED)
    private DocumentRepository repository;

    /**
     * 根据上下文进行过滤
     * @param filter 过滤器
     */
    protected void filterByContext(DocumentRepository.DocumentQueryFilter filter) {
        Context context = context();
        if (context.hasDataPermission(Constants.DATA_PERMISSION_ALL)) {
            return;
        }
        User user = context.getCurrentUser();
        if (context.hasDataPermission(Constants.DATA_PERMISSION_TENANT)) {
            filter.tenantNameField().eq(user.getTenantName());
        } else {
            filter.tenantNameField().eq(user.getTenantName());
            filter.usernameField().eq(user.getName());
        }
    }

    @Override
    protected void filterById(DocumentRepository.DocumentQueryFilter filter, Long id) {
        filterByContext(filter);
        filter.idField().eq(id);
    }

    @Override
    protected void filterByIds(DocumentRepository.DocumentQueryFilter filter, Collection<Long> ids) {
        filterByContext(filter);
        filter.idField().in(ids);
    }

    @Override
    protected boolean checkEntityForAdd(Document entity) {
        boolean duplicated = getRepository().isExistedForWrite(filter -> {
            filter.tenantNameField().eq(entity.getUser().getTenantName());
            filter.nameField().eq(entity.getName());
        });
        if (duplicated) {
            throw new DataDuplicateException();
        }
        return true;
    }

    @Override
    public Long add(Document entity) {
        entity.setUser(context().getCurrentUser());
        entity.setCreateTime(LocalDateTime.now());
        return super.add(entity);
    }

    @Override
    protected boolean checkEntityForUpdate(Document entity) {
        Optional<Document> document = getRepository().getForWrite(filter -> filterById(filter, entity.getId()));
        if (document.isEmpty()) {
            return false;
        }
        if (Objects.equals(entity.getName(), document.get().getName())) {
            return true;
        }
        boolean duplicated = getRepository().isExistedForWrite(filter -> {
            filter.tenantNameField().eq(document.get().getUser().getTenantName());
            filter.nameField().eq(entity.getName());
        });
        if (duplicated) {
            throw new DataDuplicateException();
        }
        return true;
    }

    @Override
    protected void updateByEntity(DocumentRepository.DocumentUpdater updater, Document entity) {
        updater.fromDocument(entity);
    }

    @Override
    public boolean update(Document entity) {
        entity.setUser(context().getCurrentUser());
        entity.setUpdateTime(LocalDateTime.now());
        return super.update(entity);
    }

    @Override
    public DocumentRepository.DocumentQuerier querier() {
        DocumentRepository.DocumentQuerier querier = getRepository().querier();
        querier.filter(this::filterByContext);
        return querier;
    }
}
