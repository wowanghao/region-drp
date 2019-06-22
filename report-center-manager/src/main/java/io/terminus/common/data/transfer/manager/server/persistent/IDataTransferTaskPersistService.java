package io.terminus.common.data.transfer.manager.server.persistent;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;

import java.util.Map;

/**
 * 导入导出任务写服务接口
 *
 * Created by CodeGen .
 */
public interface IDataTransferTaskPersistService {

    class Criteria {
        public static final String TASK_NAME = "name";
        public static final String TASK_TYPE = "type";
        public static final String FRONT_STATUS = "frontStatus";
        public static final String CREATED_AT_START = "createdAtStart";
        public static final String CREATED_AT_END = "createdAtEnd";
        public static final String DISP_NAME= "displayName";
        public static final String USER_ID = "userId";
        public static final String USER_NAME = "userName";
    }

    DataTransferTask findById(Long id);

    Boolean update(DataTransferTask task);

    Long create(DataTransferTask task);

    Paging<DataTransferTask> paging(PagingCriteria paging, Map<String, Object> criteria);

}