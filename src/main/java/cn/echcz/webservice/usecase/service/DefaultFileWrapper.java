package cn.echcz.webservice.usecase.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

/**
 * 默认文件包装器
 */
@AllArgsConstructor
public class DefaultFileWrapper implements FileWrapper {
    @Getter
    private final InputStream inputStream;
    @Getter
    private final long fileSize;
    @Getter
    private final String filename;
}
