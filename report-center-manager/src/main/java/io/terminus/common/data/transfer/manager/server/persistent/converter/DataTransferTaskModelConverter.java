package io.terminus.common.data.transfer.manager.server.persistent.converter;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.manager.server.persistent.model.DataTransferTaskModel;
import org.mapstruct.Mapper;

/**
 * @author yushuo
 */
@Mapper(componentModel = "spring")
public interface DataTransferTaskModelConverter {

    DataTransferTask model2Task(DataTransferTaskModel model);
    DataTransferTaskModel task2Model(DataTransferTask model);

}
