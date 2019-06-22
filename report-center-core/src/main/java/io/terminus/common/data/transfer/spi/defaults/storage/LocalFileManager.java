package io.terminus.common.data.transfer.spi.defaults.storage;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class LocalFileManager implements IFileManager {

    private final String baseDir;

    private static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat fileNameFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public LocalFileManager(String baseDir) {
        this.baseDir = baseDir.replaceFirst("^~",System.getProperty("user.home"));
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
    public String getFileUrl(String filePath) {
        return "file://" + Paths.get(baseDir).resolve(filePath).toAbsolutePath().toString();
    }

    @Override
    public void saveFile(String filePath, File file) throws IOException {
        FileUtils.copyFile(file, new File(filePath));
    }

    @Override
    public boolean deleteFile(String filePath) throws IOException {
        if(StringUtils.isEmpty(filePath)) {
            return true;
        }
        Files.delete(Paths.get(baseDir).resolve(filePath));
        return true;
    }

    @Override
    public InputStream openStream(String filePath) throws IOException{
        return new FileInputStream(Paths.get(baseDir).resolve(filePath).toFile());
    }
}
