package io.terminus.common.data.transfer.spi.ifaces;

import io.terminus.common.data.transfer.api.service.dto.TaskExecuteResult;
import io.terminus.common.data.transfer.spi.exceptions.DataTransferException;
import io.terminus.common.data.transfer.spi.models.DataExportContext;
import io.terminus.common.model.Criteria;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public interface IDataExporter<T, C extends DataExportContext> {

    Paging<T> exportData(PagingCriteria criteria, C context) throws DataTransferException;

    default void exportStarted(Criteria criteria, C context) throws DataTransferException { }

    default void exportFinished(TaskExecuteResult executeResult, C context) throws DataTransferException{ }

    default int batchSize(C context) {
        return 300;
    }

    default Map<String, String> headerAlias() {
        return null;
    }

    @SuppressWarnings("unchecked")
    default Class<T> modelClazz() {
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        for(Type genericInterface : genericInterfaces) {
            if(genericInterface instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericInterface;
                if(pType.getRawType().getTypeName().equalsIgnoreCase(IDataExporter.class.getTypeName())
                        && (pType.getActualTypeArguments().length > 0)) {
                    Object argType = pType.getActualTypeArguments()[0];
                    if (argType instanceof ParameterizedType) {
                        return (Class<T>) ((ParameterizedType) argType).getRawType();
                    } else {
                        return (Class<T>) argType;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    default Class<C> contextClazz() {
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) genericInterface;
                if (pType.getRawType().getTypeName().equalsIgnoreCase(IDataExporter.class.getTypeName())
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
        return (Class<C>) DataExportContext.class;
    }

    /**
     * 返回条件的Class。如果覆盖这个类，会自动用task.criteriaJson来反序列化构造这个类的实例,
     *   同时，export方法中，需要把pagingCriteria强转成自己的类来使用
     * 不推荐使用这个类。额外的导出条件应该推荐放在ExportContext中，业务实现自行解析criteriaJson来实现，来保持业务代码的控制力
     * @return criteria class you wanted
     */
    default Class<? extends PagingCriteria> criteriaClazz() {
        return PagingCriteria.class;
    }

}
