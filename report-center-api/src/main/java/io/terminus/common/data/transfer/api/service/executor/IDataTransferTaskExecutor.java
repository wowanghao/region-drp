package io.terminus.common.data.transfer.api.service.executor;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.model.Response;


/**
 * @author yushuo
 */

public interface IDataTransferTaskExecutor {

    /**
     * 触发执行task
     * @param task 需要触发执行的task
     * @return 是否成功触发
     */
    Response<Boolean> executeTask(DataTransferTask task);

}
