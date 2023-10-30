package cn.echcz.webservice.adapter.service;

import cn.echcz.webservice.usecase.service.DefaultFileWrapper;
import cn.echcz.webservice.usecase.service.FileStorageService;
import cn.echcz.webservice.usecase.service.FileWrapper;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * S3文件存储服务
 */
@Component
@ConditionalOnProperty(prefix = "fs", value = "type", havingValue = "s3")
public class S3FileStorageService implements FileStorageService {
    private final AmazonS3 s3Client;
    /**
     * 当使用相对路径时，以此值作为存储桶
     */
    private final String defaultBucket;
    /**
     * 当使用相对路径时，以此值作为基础key
     */
    private final String baseKey;

    public S3FileStorageService(
            @Value("${fs.s3.base-dir:}")
            String baseDir,
            @Value("${fs.s3.access-key}")
            String accessKeyId,
            @Value("${fs.s3.access-secret}")
            String accessKeySecret,
            @Value("${fs.s3.region:}")
            String region,
            @Value("${fs.s3.endpoint:}")
            String endpoint,
            @Value("${fs.s3.path-style-access:false}")
            Boolean pathStyleAccess,
            @Value("${fs.s3.disable-chunked-encoding:false}")
            Boolean disableChunkedEncoding
    ) {
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, accessKeySecret)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withPathStyleAccessEnabled(pathStyleAccess)
                .withChunkedEncodingDisabled(disableChunkedEncoding)
                .build();
        baseDir = baseDir.replaceAll("(^[/ ]+|[/ ]+$)", "");
        int idx = baseDir.indexOf("/");
        if (idx >= 0) {
            this.defaultBucket = baseDir.substring(0, idx);
            this.baseKey = baseDir.substring(idx + 1) + "/";
        } else {
            defaultBucket = baseDir;
            baseKey = "";
        }
    }

    private String[] splitPath(String path) {
        String bucket;
        String key;
        if (path.startsWith("/")) {
            int idx = path.indexOf("/", 1);
            bucket = path.substring(1, idx);
            key = path.substring(idx + 1);
        } else {
            bucket = this.defaultBucket;
            key = this.baseKey + path;
        }
        return new String[]{ bucket, key };
    }

    @Override
    public void write(String path, InputStream inputStream, long contentLength) {
        String[] bucketAndKey = splitPath(path);
        ObjectMetadata metadata = new ObjectMetadata();
        if (contentLength >= 0) {
            metadata.setContentLength(contentLength);
        }
        this.s3Client.putObject(bucketAndKey[0], bucketAndKey[1], inputStream, metadata);
    }

    @Override
    public FileWrapper read(String path) {
        String[] bucketAndKey = splitPath(path);
        S3Object s3Object = this.s3Client.getObject(bucketAndKey[0], bucketAndKey[1]);
        long contentLength = s3Object.getObjectMetadata().getContentLength();
        return new DefaultFileWrapper(s3Object.getObjectContent(), contentLength, getFilename(bucketAndKey[1]));
    }

    private String getFilename(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    @Override
    public void copy(String srcPath, String targetPath) {
        String[] srcBucketAndKey = splitPath(srcPath);
        String[] targetBucketAndKey = splitPath(targetPath);
        this.s3Client.copyObject(srcBucketAndKey[0], srcBucketAndKey[1], targetBucketAndKey[0], targetBucketAndKey[1]);
    }

    @Override
    public boolean exists(String path) {
        String[] srcBucketAndKey = splitPath(path);
        return this.s3Client.doesObjectExist(srcBucketAndKey[0], srcBucketAndKey[1]);
    }

    public void delete(String path) {
        String[] srcBucketAndKey = splitPath(path);
        this.s3Client.deleteObject(srcBucketAndKey[0], srcBucketAndKey[1]);
    }

    @Override
    public String eTag(String path) {
        String[] srcBucketAndKey = splitPath(path);
        ObjectMetadata objectMetadata = this.s3Client.getObjectMetadata(srcBucketAndKey[0], srcBucketAndKey[1]);
        return objectMetadata.getETag();
    }

    /**
     * 列出所有的存储桶
     */
    public List<String> listBuckets() {
        List<Bucket> buckets = this.s3Client.listBuckets();
        return buckets.stream().map(Bucket::getName).toList();
    }

    /**
     * 创建存储桶
     * @param bucketName 桶名
     */
    public void createBucket(String bucketName) {
        this.s3Client.createBucket(bucketName);
    }

    /**
     * 删除存储桶
     * @param bucketName 桶名
     */
    public void deleteBucket(String bucketName) {
        this.s3Client.deleteBucket(bucketName);
    }

}
