package cn.echcz.webservice.adapter.controller;

import cn.echcz.webservice.entity.ActionType;
import cn.echcz.webservice.usecase.FileUsecase;
import cn.echcz.webservice.usecase.service.FileWrapper;
import cn.echcz.webservice.util.Constants;
import cn.echcz.webservice.adapter.aspect.ActionLogger;
import cn.echcz.webservice.adapter.aspect.ActionLoggerGroup;
import cn.echcz.webservice.adapter.model.FileResource;
import cn.echcz.webservice.adapter.model.FileUploadRO;
import cn.echcz.webservice.adapter.service.HttpRequestFileWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "File", description = "文件")
@SecurityRequirements({
        @SecurityRequirement(name = HttpHeaders.AUTHORIZATION),
        @SecurityRequirement(name = Constants.HTTP_HEADER_DATA_PERMISSION),
})
@RestController
@RequestMapping("/files")
@ActionLoggerGroup("file")
@Validated
public class FileController {
    @Setter(onMethod_ = @Autowired)
    private FileUsecase fileUsecase;

    @Operation(summary = "上传文件",
            requestBody = @RequestBody(description = "文件二进制数据", required = true,
                    content = @Content(schema = @Schema(type = "string", format = "binary"))))
    @PostMapping(value = "/{basePath}", consumes = MediaType.ALL_VALUE)
    @ActionLogger(name = "上传文件", type = ActionType.CREATE,
            content = "path:" + ActionLogger.RETURN_KEY + ".path,basePath:basePath,filename:filename")
    public FileUploadRO uploadFile(
            @PathVariable String basePath, @RequestParam(defaultValue = "") String filename,
            HttpServletRequest request) {
        int i = filename.lastIndexOf(".");
        String filenameExt = i > 0 ? filename.substring(i) : "";
        FileWrapper fileWrapper = new HttpRequestFileWrapper(request, filename);
        String path = fileUsecase.upload(basePath, filenameExt, fileWrapper);
        return new FileUploadRO(path);
    }

    @Operation(summary = "下载文件",
            responses = @ApiResponse(description = "文件二进制数据",
                    content = @Content(schema = @Schema(type = "string", format = "binary"))))
    @GetMapping(value = "/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource downloadFile(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String path = servletPath.substring(7);
        return new FileResource(fileUsecase.download(path));
    }
}
