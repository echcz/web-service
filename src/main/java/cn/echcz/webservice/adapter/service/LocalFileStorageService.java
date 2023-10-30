package cn.echcz.webservice.adapter.service;

import cn.echcz.webservice.usecase.service.FileStorageService;
import cn.echcz.webservice.usecase.service.FileWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * 本地文件存储服务
 */
@Component
@ConditionalOnProperty(prefix = "fs", value = "type", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageService implements FileStorageService {
    /**
     * 不以 / 开头的 {@code path} 会以此目录为基础目录
     */
    private final Path baseDir;

    public LocalFileStorageService(
            @Value("${fs.local.base-dir:}")
            String baseDir
    ) {
        this.baseDir = Path.of(baseDir);
        if (Files.isRegularFile(this.baseDir)) {
            throw new IllegalArgumentException(this.baseDir + "已存在，但其是一个普通文件，请确保其不存在或是一个文件夹");
        }
        try {
            Files.createDirectories(this.baseDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path newPath(String path) {
        if (path.startsWith("/")) {
            return Path.of(path);
        } else {
            return this.baseDir.resolve(path);
        }
    }

    @Override
    public void write(String path, InputStream inputStream, long contentLength) {
        Path filePath = newPath(path);
        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public FileWrapper read(String path) {
        return new LocalFileWrapper(newPath(path));
    }

    @Override
    public void delete(String path) {
        deleteIfExists(path);
    }

    @Override
    public void copy(String srcPath, String targetPath) {
        Path sPath = newPath(srcPath);
        Path tPath = newPath(targetPath);
        try {
            Files.createDirectories(tPath.getParent());
            Files.copy(sPath, tPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void move(String srcPath, String targetPath) {
        Path sPath = newPath(srcPath);
        Path tPath = newPath(targetPath);
        try {
            Files.createDirectories(tPath.getParent());
            Files.move(sPath, tPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean exists(String path) {
        return Files.exists(newPath(path));
    }

    @Override
    public boolean deleteIfExists(String path) {
        try {
            return Files.deleteIfExists(newPath(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
