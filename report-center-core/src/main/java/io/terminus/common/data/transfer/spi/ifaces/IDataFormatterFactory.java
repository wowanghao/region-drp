package io.terminus.common.data.transfer.spi.ifaces;

import io.terminus.common.data.transfer.api.model.FileType;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public interface IDataFormatterFactory {

    <T> IDataFormatter<T> formatter(Class<T> clazz, FileType fileType);

}
