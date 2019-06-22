package io.terminus.common.data.transfer.spi.defaults.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Slf4j
public class OSSFileManager implements IFileManager {

    private final String ossBucket;
    private final String baseDir;

    private final OSSClient ossClient;

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat fileNameFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public OSSFileManager(String endpoint, String ossBucket, String baseDir, String key, String secret) throws IOException {
        this.ossBucket = ossBucket.trim();
        this.baseDir = baseDir.trim();
        this.ossClient = new OSSClient(endpoint.trim(), key.trim(), secret.trim());
    }

    @Override
    public String generateFilePath(DataTransferTask task) {
        String randomFileName = task.getName() + "-" + fileNameFormatter.format(new Date()) + "." + task.getFileExt();
        return Paths.get(
                baseDir,
                task.getName(),
                dateFormatter.format(task.getCreatedAt()),
                randomFileName
        ).toString();
    }

    @Override
    public String getFileUrl(String relativePath) {
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = ossClient.generatePresignedUrl(ossBucket, relativePath, expiration);
        return url.toString();
    }

    public void saveFile(String path, File file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(Files.probeContentType(file.toPath()));
        metadata.setContentLength(file.length());
        metadata.setHeader("x-oss-server-side-encryption", "AES256");

        ossClient.putObject(ossBucket, path, file, metadata);
    }

    @Override
    public boolean deleteFile(String filePath) throws IOException {
        if(StringUtils.isEmpty(filePath)) {
            return true;
        }
        try {
            ossClient.deleteObject(ossBucket, filePath);
            return true;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new IOException("", e);
        }
    }

    @Override
    public InputStream openStream(String filePath) throws IOException {
        return ossClient.getObject(ossBucket, filePath).getObjectContent();
    }

}
