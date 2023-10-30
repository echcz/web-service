package cn.echcz.webservice.adapter.controller;

import cn.echcz.webservice.adapter.aspect.ActionLogger;
import cn.echcz.webservice.adapter.aspect.ActionLoggerGroup;
import cn.echcz.webservice.adapter.model.*;
import cn.echcz.webservice.entity.ActionType;
import cn.echcz.webservice.entity.Document;
import cn.echcz.webservice.exception.NotFoundException;
import cn.echcz.webservice.usecase.DocumentUsecase;
import cn.echcz.webservice.usecase.FileUsecase;
import cn.echcz.webservice.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Document", description = "文档")
@SecurityRequirements({
        @SecurityRequirement(name = HttpHeaders.AUTHORIZATION),
        @SecurityRequirement(name = Constants.HTTP_HEADER_DATA_PERMISSION),
})
@RestController
@RequestMapping("/documents")
@ActionLoggerGroup("document")
@Validated
public class DocumentController {
    @Setter(onMethod_ = @Autowired)
    private DocumentUsecase documentUsecase;
    @Setter(onMethod_ = @Autowired)
    private FileUsecase fileUsecase;

    @Operation(summary = "创建文档")
    @PostMapping()
    @ActionLogger(name = "创建文档", type = ActionType.CREATE,
            content = "succeeded:" + ActionLogger.RETURN_KEY + ".succeeded,id:" + ActionLogger.RETURN_KEY + ".id,data:co")
    public EntitySaveRO createDocument(
            @RequestBody @Validated DocumentSaveCO co
    ) {
        Long id = documentUsecase.add(Document.create(co.toDocumentInitParams()));
        return new EntitySaveRO(id);
    }

    @Operation(summary = "更新文档")
    @PutMapping("/{id}")
    @ActionLogger(name = "更新文档", type = ActionType.UPDATE,
            content = "succeeded:" + ActionLogger.RETURN_KEY + ".succeeded,id:" + ActionLogger.RETURN_KEY + ".id,data:co")
    public EntitySaveRO updateDocument(
            @Parameter(description = "文档ID") @PathVariable Long id,
            @RequestBody @Validated DocumentSaveCO co) {
        boolean succeeded = documentUsecase.update(Document.create(id, co.toDocumentInitParams()));
        if (!succeeded) {
            throw new NotFoundException();
        }
        return new EntitySaveRO(id);
    }

    @Operation(summary = "删除文档")
    @DeleteMapping()
    @ActionLogger(name = "删除文档", type = ActionType.DELETE, content = "ids:ids")
    public DeleteRO deleteDocument(
            @Parameter(description = "文档ID") @RequestParam(defaultValue = "") List<Long> ids
    ) {
        if (ids.isEmpty()) {
            return new DeleteRO(0);
        }
        int count = documentUsecase.deleteByIds(ids);
        return new DeleteRO(count);
    }

    @Operation(summary = "获取文档")
    @GetMapping("/{id}")
    public DocumentVO getDocument(
            @Parameter(description = "文档ID") @PathVariable Long id
    ) {
        Document document = documentUsecase.getById(id).orElseThrow(NotFoundException::new);
        return DocumentVO.fromEntity(document, this::getFileDownloadUrl);
    }

    private String getFileDownloadUrl(Document document) {
        String filePath = document.getFilePath();
        if (filePath.matches("^\\w+://")) {
            return filePath;
        }
        return fileUsecase.getDownloadUrl(filePath);
    }

    @Operation(summary = "查询文档")
    @GetMapping()
    public PageVO<DocumentVO> queryDocument(@ParameterObject @Validated DocumentQO qo) {
        PageVO<Document> vo = qo.query(documentUsecase.querier());
        return vo.map(document -> DocumentVO.fromEntity(document, this::getFileDownloadUrl));
    }
}
