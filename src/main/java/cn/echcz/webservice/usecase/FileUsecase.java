package cn.echcz.webservice.usecase;

import cn.echcz.webservice.usecase.service.FileStorageService;
import cn.echcz.webservice.usecase.service.FileWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件 用例
 */
@Service
@Slf4j
public class FileUsecase extends AbstractUsecase {
    @Setter(onMethod_ = @Autowired)
    @Getter(AccessLevel.PROTECTED)
    private ContextProvider contextProvider;

    @Setter(onMethod_ = @Autowired)
    private FileStorageService fileStorageService;

    private String baseDownloadUrl;

    @Autowired
    public void setBaseDownloadUrl(@Value("${fs.base-download-url:}") String baseDownloadUrl) {
        if (!baseDownloadUrl.endsWith("/")) {
            baseDownloadUrl = baseDownloadUrl + "/";
        }
        this.baseDownloadUrl = baseDownloadUrl;
    }

    /**
     * 上传
     * @param basePath 基础地址
     * @param filenameExt
     * @return
     */
    public String upload(String basePath, String filenameExt, FileWrapper fileWrapper) {
        String path = genPath(basePath, filenameExt);
        log.info("upload file: {} -> {}", fileWrapper.getFilename(), path);
        fileStorageService.write(path, fileWrapper);
        return path;
    }


    private final DateTimeFormatter dateFormatterForGenPath = DateTimeFormatter.ofPattern("/yyyy/MM/dd/");
    /**
     * 生成文件地址
     * @param basePath 文件基础地址
     * @param filenameExt 文件扩展名
     */
    private String genPath(String basePath, String filenameExt) {
        String tenantName = context().getCurrentUser().getTenantName();
        if (tenantName.isEmpty()) {
            tenantName = "_";
        }
        if (!basePath.startsWith("/")) {
            basePath = "/" +basePath;
        }
        String filename = UUID.randomUUID().toString().replace("-", "");
        if (!filenameExt.startsWith(".")) {
            filenameExt = "." + filenameExt;
        }

        return tenantName + basePath + LocalDate.now().format(dateFormatterForGenPath) + filename + filenameExt;
    }

    /**
     * 下载文件
     * @param path 文件地址
     */
    public FileWrapper download(String path) {
        return fileStorageService.read(path);
    }

    /**
     * 获取文件下载URL
     * @param path 文件地址
     */
    public String getDownloadUrl(String path) {
        return this.baseDownloadUrl + path;
    }

}
