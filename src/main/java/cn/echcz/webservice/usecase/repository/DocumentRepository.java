package cn.echcz.webservice.usecase.repository;

import cn.echcz.webservice.entity.Document;

import java.time.LocalDateTime;

import static cn.echcz.webservice.usecase.repository.DocumentRepository.*;

/**
 * 文档仓库
 */
public interface DocumentRepository
        extends BaseRepository<Long, Document, DocumentUpdater, DocumentQueryFilter> {
    @Override
    DocumentQuerier querier();

    /**
     * 文档更新器
     */
    interface DocumentUpdater extends DataUpdater {
        void setName(String name);

        void setFilePath(String filePath);

        void setUpdateTime(LocalDateTime updateTime);

        /**
         * 根据文档实体更新数据
         */
        default void fromDocument(Document document) {
            setName(document.getName());
            setFilePath(document.getFilePath());
            setUpdateTime(document.getUpdateTime());
        }
    }

    /**
     * 文档查询过滤器
     */
    interface DocumentQueryFilter extends QueryFilter<DocumentQueryFilter> {
        QueryField<Long> idField();

        QueryField<String> tenantNameField();

        QueryField<String> usernameField();

        QueryField<String> nameField();

        QueryField<LocalDateTime> createTimeField();
    }

    /**
     * 文档查询器
     */
    interface DocumentQuerier extends Querier<Document, DocumentQueryFilter> {
        /**
         * 按创建时间倒序排序
         */
        void orderByCreateTimeDesc();
    }
}
