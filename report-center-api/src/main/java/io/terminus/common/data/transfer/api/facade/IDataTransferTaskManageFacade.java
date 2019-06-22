package io.terminus.common.data.transfer.api.facade;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.api.model.DataTransferTaskResult;
import io.terminus.common.data.transfer.api.service.dto.*;
import io.terminus.common.model.Paging;
import io.terminus.common.model.Response;

/**
 * @author yushuo
 * 这个需要在 Manager 中实现
 */
public interface IDataTransferTaskManageFacade {

    Response<DataTransferTask> createTask(DataTransferTaskCreateRequest taskCreateRequest);

    Response<Paging<DataTransferTask>> pagingTasks(DataTransferTaskPagingRequest taskPagingRequest);

    Response<DataTransferTask> executeTaskById(DataTransferTaskExecuteRequest taskExecuteRequest);

    Response<DataTransferTask> findTaskById(DataTransferTaskQueryRequest taskQueryRequest);

    Response<DataTransferTaskResult> findTaskResultById(DataTransferTaskQueryRequest taskQueryRequest);

    Response<Boolean> taskExecuteCallback(DataTransferTaskExecuteCallbackRequest taskCallbackRequest);

    Response<Boolean> deleteTaskFileById(DataTransferTaskDeleteRequest taskDeleteRequest);

}
