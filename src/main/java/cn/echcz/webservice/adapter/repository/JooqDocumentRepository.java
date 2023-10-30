package cn.echcz.webservice.adapter.repository;

import cn.echcz.webservice.adapter.repository.tables.records.DocumentRecord;
import cn.echcz.webservice.entity.DefaultUser;
import cn.echcz.webservice.entity.Document;
import cn.echcz.webservice.entity.DocumentInitParams;
import cn.echcz.webservice.entity.User;
import cn.echcz.webservice.usecase.repository.DocumentRepository;
import cn.echcz.webservice.usecase.repository.QueryField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档 JOOQ仓库
 */
@Repository
public class JooqDocumentRepository
        extends AbstractJooqRepository<Long, Document, DocumentRecord,
        DocumentRepository.DocumentUpdater, DocumentRepository.DocumentQueryFilter>
        implements DocumentRepository {
    @Getter(value = AccessLevel.PROTECTED, onMethod_ = @Override)
    @Setter(onMethod_ = @Autowired)
    private DSLContext dslContext;

    @Override
    protected Table<DocumentRecord> getTable() {
        return Tables.DOCUMENT;
    }

    @Override
    protected TableField<DocumentRecord, Long> getPk() {
        return Tables.DOCUMENT.ID;
    }

    @Override
    protected Document recordToDataForQuery(DocumentRecord record) {
        return Document.create(record.getId(), new DocumentInitParamsAdapter(record));
    }

    @Override
    protected DocumentRecord dataToRecordForAdd(Document entity) {
        DocumentRecord record = new DocumentRecord();
        record.setName(entity.getName());
        record.setFilePath(entity.getFilePath());
        User user = entity.getUser();
        record.setTenantName(user.getTenantName());
        record.setUsername(user.getName());
        record.setCreateTime(entity.getCreateTime());
        return record;
    }

    @Override
    protected JooqDocumentUpdater getDataUpdater() {
        return new JooqDocumentUpdater(new DocumentRecord());
    }

    @Override
    protected JooqDocumentQueryFilter getQueryFilter() {
        return new JooqDocumentQueryFilter();
    }

    @Override
    public DocumentQuerier querier() {
        return new JooqDocumentQuerier();
    }

    /**
     * 文档更新器 实现
     */
    private static class JooqDocumentUpdater extends AbstractJooqDataUpdater<DocumentRecord>
            implements DocumentUpdater {

        public JooqDocumentUpdater(DocumentRecord record) {
            super(record);
        }

        @Override
        public void setName(String name) {
            getRecord().setName(name);
        }

        @Override
        public void setFilePath(String filePath) {
            getRecord().setFilePath(filePath);
        }

        @Override
        public void setUpdateTime(LocalDateTime updateTime) {
            getRecord().setUpdateTime(updateTime);
        }
    }

    /**
     * 文档查询过滤器 实现
     */
    private static class JooqDocumentQueryFilter
            extends AbstractJooqQueryFilter<DocumentQueryFilter>
            implements DocumentQueryFilter {

        @Override
        protected JooqDocumentQueryFilter getSubQueryFilter() {
            return new JooqDocumentQueryFilter();
        }

        @Override
        public QueryField<Long> idField() {
            return new JooqQueryField<>(this, Tables.DOCUMENT.ID);
        }

        @Override
        public QueryField<String> tenantNameField() {
            return new JooqQueryField<>(this, Tables.DOCUMENT.TENANT_NAME);
        }

        @Override
        public QueryField<String> usernameField() {
            return new JooqQueryField<>(this, Tables.DOCUMENT.USERNAME);
        }

        @Override
        public QueryField<String> nameField() {
            return new JooqQueryField<>(this, Tables.DOCUMENT.NAME);
        }

        @Override
        public QueryField<LocalDateTime> createTimeField() {
            return new JooqQueryField<>(this, Tables.DOCUMENT.CREATE_TIME);
        }
    }

    /**
     * 文档查询器 实现
     */
    private class JooqDocumentQuerier extends AbstractJooqQuerier implements DocumentQuerier {
        @Override
        public void orderByCreateTimeDesc() {
            setOrderFields(List.of(Tables.DOCUMENT.CREATE_TIME.desc()));
        }
    }

    /**
     * 文档JOOQ记录类转 {@link DocumentInitParams} 适配器
     */
    private static class DocumentInitParamsAdapter implements DocumentInitParams {
        private final DocumentRecord record;

        private DocumentInitParamsAdapter(DocumentRecord record) {
            this.record = record;
        }

        @Override
        public String name() {
            return record.getName();
        }

        @Override
        public String filePath() {
            return record.getFilePath();
        }

        @Override
        public User user() {
            return new DefaultUser(record.getTenantName(), record.getUsername());
        }

        @Override
        public LocalDateTime createTime() {
            return record.getCreateTime();
        }

        @Override
        public LocalDateTime updateTime() {
            return record.getUpdateTime();
        }
    }
}
