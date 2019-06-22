package io.terminus.common.data.transfer.spi.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author yushuo
 */
@NoArgsConstructor
public class DataTransferException extends Exception {

    public DataTransferException(String message) {
        super(message);
    }

    public DataTransferException(String message, Exception cause) {
        super(message, cause);
    }

}
