package io.terminus.common.data.transfer.spi.ifaces;

import com.alibaba.excel.metadata.Sheet;
import io.terminus.common.data.transfer.api.service.dto.TaskExecuteResult;
import io.terminus.common.data.transfer.spi.exceptions.DataImportInvalidRowException;
import io.terminus.common.data.transfer.spi.exceptions.DataTransferException;
import io.terminus.common.data.transfer.spi.models.BatchDataImportResult;
import io.terminus.common.data.transfer.spi.models.DataImportContext;
import io.terminus.common.data.transfer.spi.models.RowData;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * AUTHOR: zhangbin
 * ON: 2018/11/26
 */
public interface IDataImporter<T, C extends DataImportContext> {

    void importRowData(RowData<T> data, C context) throws DataImportInvalidRowException;

    default Integer batchSize() {
        return null;
    }

    default Sheet sheetProperty() {
        return new Sheet(/*第一个sheet*/1, /*标题1行*/1);
    }

    default BatchDataImportResult<T> batchImportRowData(List<RowData<T>> dataList, C context)
            throws DataTransferException {
        return BatchDataImportResult.successResultOf(dataList);
    }

    default void importStarted(C context) throws DataTransferException  {
    }

    default void importFinished(TaskExecuteResult executeResult, C context) throws DataTransferException {
    }

    @SuppressWarnings("unchecked")
    default Class<C> contextClazz() {
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        for(Type genericInterface : genericInterfaces) {
            if(genericInterface instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericInterface;
                if(pType.getRawType().getTypeName().equalsIgnoreCase(IDataImporter.class.getTypeName())
                        && pType.getActualTypeArguments().length > 1) {
                    Object argType = pType.getActualTypeArguments()[1];
                    if (argType instanceof ParameterizedType) {
                        return (Class<C>) ((ParameterizedType) argType).getRawType();
                    } else {
                        return (Class<C>) argType;
                    }
                }
            }
        }
        return (Class<C>) DataImportContext.class;
    }

    @SuppressWarnings("unchecked")
    default Class<T> modelClazz() {
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        for(Type genericInterface : genericInterfaces) {
            if(genericInterface instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericInterface;
                if(pType.getRawType().getTypeName().equalsIgnoreCase(IDataImporter.class.getTypeName())
                        && pType.getActualTypeArguments().length > 0) {
                    Object argType = pType.getActualTypeArguments()[0];
                    if (argType instanceof ParameterizedType) {
                        return (Class<T>) ((ParameterizedType) argType).getRawType();
                    } else {
                        return (Class<T>) argType;
                    }
                }
            }
        }
        return (Class<T>) List.class;
    }

}
