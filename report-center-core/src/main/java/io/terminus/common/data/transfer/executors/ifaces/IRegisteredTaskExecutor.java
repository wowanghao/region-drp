package io.terminus.common.data.transfer.executors.ifaces;

import io.terminus.common.data.transfer.api.service.executor.IDataTransferTaskExecutor;
import io.terminus.common.data.transfer.spi.ifaces.IDataExporter;
import io.terminus.common.data.transfer.spi.ifaces.IDataImporter;

/**
 * @author yushuo
 */
public interface IRegisteredTaskExecutor extends IDataTransferTaskExecutor {

    void registerImporter(String name, IDataImporter dataImporter);

    void registerExporter(String name, IDataExporter dataExporter);
}
