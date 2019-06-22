package io.terminus.common.data.transfer.manager.server.persistent.service;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.manager.server.persistent.IDataTransferTaskPersistService;
import io.terminus.common.data.transfer.manager.server.persistent.converter.DataTransferTaskModelConverter;
import io.terminus.common.data.transfer.manager.server.persistent.dao.DataTransferTaskDao;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导入导出任务写服务实现类
 *
 * Created by CodeGen .
 */
@Service
public class DataTransferTaskPersistServiceImpl implements IDataTransferTaskPersistService {

    private final DataTransferTaskDao dataTransferTaskDao;
    private final DataTransferTaskModelConverter dataConverter;

    @Autowired
    public DataTransferTaskPersistServiceImpl(
            DataTransferTaskDao dataTransferTaskDao,
            DataTransferTaskModelConverter dataConverter
    ) {
        this.dataTransferTaskDao = dataTransferTaskDao;
        this.dataConverter = dataConverter;
    }

    @Override
    public Long create(DataTransferTask task) {
        var taskModel = dataConverter.task2Model(task);
        dataTransferTaskDao.create(taskModel);
        return taskModel.getId();
    }

    @Override
    public Boolean update(DataTransferTask task) {
        var taskModel = dataConverter.task2Model(task);
        return dataTransferTaskDao.update(taskModel);
    }

    @Override
    public Paging<DataTransferTask> paging(PagingCriteria paging, Map<String, Object> criteria) {
        var models = dataTransferTaskDao.paging(paging.getOffset(), paging.getPageSize(), criteria);
        return new Paging<>(models.getTotal(),
                models.getData().stream().map(dataConverter::model2Task).collect(Collectors.toList()));
    }

    @Override
    public DataTransferTask findById(Long id) {
        return dataConverter.model2Task(dataTransferTaskDao.findById(id));
    }

}