package cn.echcz.webservice.adapter.model;

import cn.echcz.webservice.usecase.service.FileWrapper;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;

/**
 * 文件资源，封装文件数据流，用于文件下载
 */
public class FileResource extends InputStreamResource {
    private final FileWrapper fileWrapper;

    public FileResource(FileWrapper fileWrapper) {
        super(fileWrapper.getInputStream(), fileWrapper.getFilename());
        this.fileWrapper = fileWrapper;
    }

    @Override
    public String getFilename() {
        return fileWrapper.getFilename();
    }

    @Override
    public long contentLength() throws IOException {
        return fileWrapper.getFileSize();
    }
}
