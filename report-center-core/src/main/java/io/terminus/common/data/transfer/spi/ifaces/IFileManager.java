package io.terminus.common.data.transfer.spi.ifaces;

import io.terminus.common.data.transfer.api.model.DataTransferTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public interface IFileManager {

    String generateFilePath(DataTransferTask task);

    String getFileUrl(String filePath);

    void saveFile(String filePath, File file) throws IOException;

    boolean deleteFile(String filePath) throws IOException;

    InputStream openStream(String filePath) throws IOException;
}
