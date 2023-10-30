package cn.echcz.webservice.adapter.service;

import cn.echcz.webservice.usecase.service.FileWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * HTTP请求 文件包装器
 */
public class HttpRequestFileWrapper implements FileWrapper {
    private final HttpServletRequest request;
    private final String filename;

    public HttpRequestFileWrapper(HttpServletRequest request, String filename) {
        this.request = request;
        this.filename = filename;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return request.getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public long getFileSize() {
        return request.getContentLengthLong();
    }

    @Override
    public String getFilename() {
        return filename;
    }
}
