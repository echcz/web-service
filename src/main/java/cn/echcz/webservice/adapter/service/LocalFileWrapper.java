package cn.echcz.webservice.adapter.service;

import cn.echcz.webservice.usecase.service.FileWrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 本地文件包装器
 */
public class LocalFileWrapper implements FileWrapper {
    private final Path path;

    public LocalFileWrapper(Path path) {
        this.path = path;
    }

    public LocalFileWrapper(File file) {
        this.path = file.toPath();
    }

    public LocalFileWrapper(String path) {
        this.path = Path.of(path);
    }

    @Override
    public InputStream getInputStream() {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String getFilename() {
        return path.getFileName().toString();
    }

    @Override
    public byte[] toBytes() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
