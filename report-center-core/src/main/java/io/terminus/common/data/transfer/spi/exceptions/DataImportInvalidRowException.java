package io.terminus.common.data.transfer.spi.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author yushuo
 */
@NoArgsConstructor
public class DataImportInvalidRowException extends DataTransferException {

    public DataImportInvalidRowException(String message) {
        super(message);
    }

    public DataImportInvalidRowException(String message, Exception cause) {
        super(message, cause);
    }

}
