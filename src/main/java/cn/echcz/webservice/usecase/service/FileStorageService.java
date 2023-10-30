package cn.echcz.webservice.usecase.service;

import com.google.common.hash.Hashing;

import java.io.*;

/**
 * 文件存储服务
 */
public interface FileStorageService {
    /**
     * 写文件
     * <br/>NOTE: 这不会关闭传入的 {@code inputStream}，请手动关闭它
     * @param path 文件路径
     * @param inputStream 数据流
     * @param contentLength 数据长度，如果无法获取长度，请传递负数
     */
    void write(String path, InputStream inputStream, long contentLength);

    /**
     * 写文件
     * <br/>NOTE: 这自动关闭 {@code fileWrapper} 的 {@code inputStream}
     * @param path 文件路径
     * @param fileWrapper 文件包装器
     * @return eTag，如果支持
     */
    default void write(String path, FileWrapper fileWrapper) {
        try (InputStream inputStream = fileWrapper.getInputStream()) {
            write(path, inputStream, fileWrapper.getFileSize());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 写文件
     * @param path 文件路径
     * @param data 数据
     * @return eTag，如果支持
     */
    default void write(String path, byte[] data) {
        write(path, new ByteArrayInputStream(data), data.length);
    }

    /**
     * 读取文件
     * @param path 文件路径
     */
    FileWrapper read(String path);

    /**
     * 读文件到字节数组中
     * @param path 文件路径
     */
    default byte[] readAsBytes(String path) {
        return read(path).toBytes();
    }

    /**
     * 读文件到流中
     * <br/>NOTE: 这不会关闭传入的 {@code outputStream}，请手动关闭它
     * @param path 文件路径
     * @param outputStream 输出流
     * @return 内容长度
     */
    default long readToStream(String path, OutputStream outputStream) {
        return read(path).transferTo(outputStream);
    }

    /**
     * 复制文件
     * @param srcPath 源路径
     * @param targetPath 目标路径
     * @return 目标文件的 eTag，如果支持
     */
    default void copy(String srcPath, String targetPath) {
        write(targetPath, read(srcPath));
    }

    /**
     * 删除文件
     * <br/>NOTE: 即使文件不存在也会不会报错，而是会保证在执行此操作后，文件不存在
     */
    void delete(String path);

    /**
     * 移动文件
     * @param srcPath 源路径
     * @param targetPath 目标路径
     * @return 目标文件的 eTag，如果支持
     */
    default void move(String srcPath, String targetPath) {
        copy(srcPath, targetPath);
        delete(srcPath);
    }

    /**
     * 文件是否存在
     */
    boolean exists(String path);

    /**
     * 如果指定文件存在，则删除指定文件，并返回true，否则返回false
     * <br/>此操作不保证原子性
     */
    default boolean deleteIfExists(String path) {
        if (exists(path)) {
            delete(path);
            return true;
        }
        return false;
    }

    /**
     * 查询指定文件的ETag值
     */
    default String eTag(String path) {
        return Hashing.sha256().hashBytes(readAsBytes(path)).toString();
    }
}
