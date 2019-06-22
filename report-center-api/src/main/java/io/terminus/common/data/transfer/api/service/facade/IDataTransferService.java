package io.terminus.common.data.transfer.api.service.facade;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.api.model.FileType;
import io.terminus.common.model.Paging;
import io.terminus.common.model.Response;

import java.util.Map;

/**
 * date 2018/9/28
 *
 * @author yushuo
 */
@Deprecated
public interface IDataTransferService {

    Response<DataTransferTask> createExportTask(String name, FileType fileType, String desc, String criteriaJson);

    Response<DataTransferTask> getTaskById(Long taskId);

    Response<DataTransferTask> runTaskById(Long taskId);

    Response<String> getTaskFileUrl(Long taskId);

    Response<Boolean> deleteTaskFile(Long taskId);

    Response<Paging<DataTransferTask>> listTasks(int pageNo, int pageSize, Map<String, Object> c);

    Response<DataTransferTask> createImportTask(String name, String desc, String filePath, FileType fileType);
}
