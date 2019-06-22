package io.terminus.common.data.transfer.spi.ifaces;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * date 2018/9/26
 * Implements of writeLine should write header first
 * should either implement writeLine, or flush
 *
 * @author yushuo
 */
public interface IDataFormatter<T> {

    void writeLine(T data, OutputStream outputStream);

    default void flush(OutputStream outputStream) throws IOException {};

    void setHeaderAlias(Map<String, String> alias);
}
