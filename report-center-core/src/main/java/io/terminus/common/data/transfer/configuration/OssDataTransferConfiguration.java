package io.terminus.common.data.transfer.configuration;

import io.terminus.common.data.transfer.spi.defaults.storage.OSSFileManager;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Configuration
@ConditionalOnProperty(value = "data.transfer.storage.active", havingValue = "oss", matchIfMissing = true)
public class OssDataTransferConfiguration implements IDataTransferConfiguration {

    @Value("${data.transfer.storage.oss.baseDir:data/transfer}")
    private String baseDir;

    @Value("${data.transfer.storage.oss.endpoint}")
    private String ossEndpoint;

    @Value("${data.transfer.storage.oss.bucket}")
    private String ossBucket;

    @Value("${data.transfer.storage.oss.key}")
    private String ossKey;

    @Value("${data.transfer.storage.oss.secret}")
    private String ossSecret;

    @Bean("fileManager")
    @Override
    public IFileManager fileManager() throws Exception {
        return new OSSFileManager(ossEndpoint, ossBucket, baseDir, ossKey, ossSecret);
    }

}
