package io.terminus.common.data.transfer.spi.defaults.formatter;

import io.terminus.common.data.transfer.api.model.FileType;
import io.terminus.common.data.transfer.spi.ifaces.IDataFormatter;
import io.terminus.common.data.transfer.spi.ifaces.IDataFormatterFactory;

import java.text.MessageFormat;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

public class DefaultDataFormatterFactory implements IDataFormatterFactory {

    @Override
    public <T> IDataFormatter<T> formatter(Class<T> clazz, FileType fileType) {
        if(null == fileType) {
            throw new IllegalArgumentException("Export filetype should not null");
        }
        switch (fileType) {
            case XLS:
                return new XlsDataFormatter<T>(clazz);
            default:
                throw new IllegalArgumentException(MessageFormat.format(
                        "Unsupported export format {0}.", fileType.getExtName()
                ));
        }
    }
}
