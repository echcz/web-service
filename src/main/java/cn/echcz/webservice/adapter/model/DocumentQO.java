package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.entity.Document;
import cn.echcz.webservice.usecase.repository.DocumentRepository;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 文档查询对象
 */
@Schema(description = "文档查询对象", accessMode = Schema.AccessMode.WRITE_ONLY)
public record DocumentQO(
        @Schema(description = "文档名称，模糊匹配")
        String name,
        @Schema(description = "创建时间最小值")
        LocalDateTime createTimeMin,
        @Schema(description = "创建时间最大值")
        LocalDateTime createTimeMax,
        @Schema(description = "查询偏移量")
        int offset,
        @Schema(description = "查询限制量")
        int limit
) {
    public PageVO<Document> query(DocumentRepository.DocumentQuerier querier) {
        querier.filter(filter -> {
            if (!Strings.isNullOrEmpty(name)) {
                filter.nameField().likeMid(name);
            }
            if (Objects.nonNull(createTimeMin)) {
                filter.createTimeField().ge(createTimeMin);
            }
            if (Objects.nonNull(createTimeMax)) {
                filter.createTimeField().le(createTimeMax);
            }
        });
        int count = querier.count();
        if (count == 0) {
            return PageVO.empty();
        }
        querier.orderByCreateTimeDesc();
        List<Document> data = querier.list(offset, limit);
        return new PageVO<>(count, data);
    }
}
