package cn.echcz.webservice.usecase.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public interface FileWrapper {
    /**
     * 获取文件流
     */
    InputStream getInputStream();

    /**
     * 获取文件大小，如果文件大小未知，则返回负数
     */
    long getFileSize();

    /**
     * 获取文件名
     */
    String getFilename();

    /**
     * 转换为字节数组
     */
    default byte[] toBytes() {
        Long fileSize = getFileSize();
        if (fileSize >= 0L) {
            byte[] bytes = new byte[fileSize.intValue()];
            try (InputStream inputStream = getInputStream()) {
                inputStream.readNBytes(fileSize.intValue());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return bytes;
        } else {
            try (InputStream inputStream = getInputStream()) {
                return inputStream.readAllBytes();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * 传输流，这将关闭 {@code inputStream}，但不会关闭 {@code outputStream}
     * @return 传输的字节数
     */
    default long transferTo(OutputStream outputStream) {
        try (InputStream inputStream = getInputStream()) {
            return inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
