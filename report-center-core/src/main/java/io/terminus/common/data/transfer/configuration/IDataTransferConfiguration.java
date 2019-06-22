package io.terminus.common.data.transfer.configuration;

import io.terminus.common.data.transfer.spi.defaults.formatter.DefaultDataFormatterFactory;
import io.terminus.common.data.transfer.spi.ifaces.IDataFormatterFactory;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import org.springframework.context.annotation.Bean;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public interface IDataTransferConfiguration {

    @Bean
    default IDataFormatterFactory dataFormatterFactory() {
        return new DefaultDataFormatterFactory();
    }

    // Should annotated by @Bean
    IFileManager fileManager() throws Exception;

}
