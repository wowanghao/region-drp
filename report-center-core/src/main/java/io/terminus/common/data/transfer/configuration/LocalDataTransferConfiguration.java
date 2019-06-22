package io.terminus.common.data.transfer.configuration;

import io.terminus.common.data.transfer.spi.defaults.storage.LocalFileManager;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2018/10/10
 *
 * @author yushuo
 */
@Configuration
@ConditionalOnProperty(value = "data.transfer.storage.active", havingValue = "local")
public class LocalDataTransferConfiguration implements IDataTransferConfiguration {

    @Value("${data.transfer.storage.local.baseDir}")
    private String baseDir;

    @Bean("fileManager")
    @Override
    public IFileManager fileManager() throws Exception {
        return new LocalFileManager(baseDir);
    }

}
