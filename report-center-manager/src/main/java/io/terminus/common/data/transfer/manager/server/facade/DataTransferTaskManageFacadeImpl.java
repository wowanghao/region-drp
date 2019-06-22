package io.terminus.common.data.transfer.manager.server.facade;

import com.alibaba.dubbo.rpc.RpcException;
import io.terminus.boot.rpc.dubbo.light.consumer.ServiceSubscriber;
import io.terminus.common.data.transfer.api.exceptions.DataTransferErrorCode;
import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.api.model.DataTransferTaskResult;
import io.terminus.common.data.transfer.api.model.DataTransferTaskStatus;
import io.terminus.common.data.transfer.api.model.DataTransferTaskType;
import io.terminus.common.data.transfer.api.service.executor.IDataTransferTaskExecutor;
import io.terminus.common.data.transfer.api.facade.IDataTransferTaskManageFacade;
import io.terminus.common.data.transfer.api.service.dto.*;
import io.terminus.common.data.transfer.manager.server.persistent.IDataTransferTaskPersistService;
import io.terminus.common.data.transfer.spi.ifaces.IFileManager;
import io.terminus.common.exception.ServiceException;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;
import io.terminus.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yushuo
 */
@Slf4j
@Service
public class DataTransferTaskManageFacadeImpl implements IDataTransferTaskManageFacade {

    private final IFileManager fileManager;
    private final IDataTransferTaskPersistService dataTransferTaskPersistService;
    private final ServiceSubscriber serviceSubscriber;

    private final ConcurrentHashMap<String, IDataTransferTaskExecutor> taskExecutors = new ConcurrentHashMap<>();

    @Autowired
    public DataTransferTaskManageFacadeImpl(
            IFileManager fileManager,
            ServiceSubscriber serviceSubscriber,
            IDataTransferTaskPersistService dataTransferTaskPersistService) {
        // 这里的fileManager, report-center-manager用的必须和各中心使用的相一致，否则无法正常生成下载链接
        // fileManager相一致，也包括OSS的配置必须一致。
        // LocalFileManager 只作为本地调试使用，线上集群部署的方式下，无法正常提供功能
        this.fileManager = fileManager;
        this.serviceSubscriber = serviceSubscriber;
        this.dataTransferTaskPersistService = dataTransferTaskPersistService;
    }

    @Override
    public Response<DataTransferTask> createTask(DataTransferTaskCreateRequest request) {
        try {
            var task = DataTransferTask.builder()
                    .name(request.getName())
                    .displayName(request.getDisplayName())
                    .desc(request.getDesc())
                    .userId(request.getUserId())
                    .userName(request.getUserName())
                    .userNickname(request.getUserNickname())
                    .type(request.getTaskType())
                    .fileExt(request.getFileExt())
                    .createdAt(new Date())
                    .status(DataTransferTaskStatus.PENDING.getCode())
                    .criteriaJson(request.getCriteriaJson())
                    .build();

            if (DataTransferTaskType.IMPORT.getCode() == request.getTaskType()) {
                if (StringUtils.isBlank(request.getFilePath())) {
                    return Response.fail(DataTransferErrorCode.IMPORT_TASK_FILE_INVALID.getCode());
                }
                task.setFilePath(request.getFilePath());
            }

            Long taskId = dataTransferTaskPersistService.create(task);
            task.setId(taskId);

            trigExecuteTask(task);
            return Response.ok(task);
        } catch (ServiceException se) {
            return Response.fail(se.getMessage());
        }
    }

    @Override
    public Response<Paging<DataTransferTask>> pagingTasks(DataTransferTaskPagingRequest taskPagingRequest) {
        PagingCriteria page = new PagingCriteria();
        page.setPageNo(taskPagingRequest.getPageNo());
        page.setPageSize(taskPagingRequest.getPageSize());

        Map<String, Object> criteria = new HashMap<>();
        criteria.put(IDataTransferTaskPersistService.Criteria.TASK_NAME, taskPagingRequest.getName());
        criteria.put(IDataTransferTaskPersistService.Criteria.TASK_TYPE, taskPagingRequest.getTaskType());
        criteria.put(IDataTransferTaskPersistService.Criteria.DISP_NAME, taskPagingRequest.getDisplayName());
        criteria.put(IDataTransferTaskPersistService.Criteria.USER_ID, taskPagingRequest.getUserId());
        criteria.put(IDataTransferTaskPersistService.Criteria.USER_NAME, taskPagingRequest.getUserName());
        criteria.put(IDataTransferTaskPersistService.Criteria.FRONT_STATUS, taskPagingRequest.getFrontStatus());
        criteria.put(IDataTransferTaskPersistService.Criteria.CREATED_AT_START, taskPagingRequest.getCreateTimeStart());
        criteria.put(IDataTransferTaskPersistService.Criteria.CREATED_AT_END, taskPagingRequest.getCreateTimeEnd());

        Paging<DataTransferTask> dataTransferTasks = dataTransferTaskPersistService.paging(page, criteria);

        List<DataTransferTask> exportTasks = dataTransferTasks.getData().stream().map(t -> {
            DataTransferTask ret = new DataTransferTask();
            BeanUtils.copyProperties(t, ret);
            return ret;
        }).collect(Collectors.toList());
        return Response.ok(new Paging<>(dataTransferTasks.getTotal(), exportTasks));
    }

    @Override
    public Response<DataTransferTask> executeTaskById(DataTransferTaskExecuteRequest taskExecuteRequest) {
        try {
            DataTransferTask task = dataTransferTaskPersistService.findById(taskExecuteRequest.getTaskId());

            if ( !taskExecuteRequest.isAdmin() && !Objects.equals(task.getUserId(), taskExecuteRequest.getUserId())) {
                return Response.fail(DataTransferErrorCode.TASK_USER_MISMATCH.getCode());
            }

            if (task.getStatus() > 0 && null != task.getStartedAt()
                    && new DateTime(task.getStartedAt()).plusMinutes(10).toDate().after(new Date())) {
                return Response.fail(DataTransferErrorCode.TASK_EXECUTE_INTERVAL_LIMIT.getCode());
            }
            trigExecuteTask(task);
            return Response.ok(task);
        } catch (ServiceException se) {
            return Response.fail(se.getMessage());
        }
    }

    private void trigExecuteTask(DataTransferTask task) {
        try {
            IDataTransferTaskExecutor executor = findExecutorForTask(task);
            executor.executeTask(task);
            task.setStartedAt(new Date());
            task.setStatus(DataTransferTaskStatus.RUNNING.getCode());
        } catch (RpcException rpcException) {
            log.error("trigExecuteTask error", rpcException);
            task.setStatus(DataTransferTaskStatus.ERROR.getCode());
            if(rpcException.isForbidded()) {
                task.setMessage(DataTransferErrorCode.TASK_HAS_NO_PROVIDER.getCode());
                throw new ServiceException(DataTransferErrorCode.TASK_HAS_NO_PROVIDER.getCode(), rpcException);
            } else {
                task.setMessage(rpcException.getClass().getSimpleName() + ":" + rpcException.getMessage());
                throw rpcException;
            }
        } catch (Exception e) {
            log.error("trigExecuteTask error", e);
            task.setStatus(DataTransferTaskStatus.ERROR.getCode());
            task.setMessage(e.getClass().getSimpleName() + ":" + e.getMessage());
            throw e;
        } finally {
            dataTransferTaskPersistService.update(task);
        }
    }

    private IDataTransferTaskExecutor findExecutorForTask(DataTransferTask task) {
        String taskKey = DataTransferTaskType.fromCode(task.getType()) + "__" + task.getName();
        return taskExecutors.computeIfAbsent(taskKey, groupId -> {
            return serviceSubscriber.consumer(IDataTransferTaskExecutor.class)
                    .group(groupId).timeout(5000).subscribe();
        });
    }

    @Override
    public Response<DataTransferTask> findTaskById(DataTransferTaskQueryRequest taskQueryRequest) {
        var task = dataTransferTaskPersistService.findById(taskQueryRequest.getTaskId());

        if((!taskQueryRequest.isAdmin()) && !Objects.equals(task.getUserId(), taskQueryRequest.getUserId())) {
            return Response.fail(DataTransferErrorCode.TASK_USER_MISMATCH.getCode());
        }

        return Response.ok(task);
    }


    @Override
    public Response<DataTransferTaskResult> findTaskResultById(DataTransferTaskQueryRequest taskQueryRequest) {
        DataTransferTask task = dataTransferTaskPersistService.findById(taskQueryRequest.getTaskId());
        if(null == task) {
            return Response.ok(null);
        }
        if((!taskQueryRequest.isAdmin()) && !Objects.equals(task.getUserId(), taskQueryRequest.getUserId())) {
            return Response.fail(DataTransferErrorCode.TASK_USER_MISMATCH.getCode());
        }

        DataTransferTaskResult ret = new DataTransferTaskResult();
        ret.setTaskId(task.getId());
        ret.setErrorCount(task.getErrorCount());
        ret.setSuccessCount(task.getSuccessCount());
        ret.setMessage(task.getMessage());
        ret.setExtraJson(task.getExtraJson());
        ret.setStatus(task.getStatus());
        if(StringUtils.isNotEmpty(task.getFilePath())) {
            ret.setFileUrl(fileManager.getFileUrl(task.getFilePath()));
        }
        if(StringUtils.isNotEmpty(task.getErrorRecordsFilePath())) {
            ret.setErrorRecordsFileUrl(fileManager.getFileUrl(task.getErrorRecordsFilePath()));
        }
        return Response.ok(ret);
    }

    @Override
    public Response<Boolean> taskExecuteCallback(DataTransferTaskExecuteCallbackRequest taskCallbackRequest) {
        try {
            if(null == taskCallbackRequest.getTaskId()) {
                throw new IllegalArgumentException(DataTransferErrorCode.TASK_ID_IS_REQUIRED.getCode());
            }
            DataTransferTask task = dataTransferTaskPersistService.findById(taskCallbackRequest.getTaskId());
            if(task == null) {
                throw new IllegalArgumentException(DataTransferErrorCode.TASK_NOT_FOUND.getCode());
            }

            TaskExecuteResult taskExecuteResult = taskCallbackRequest.getTaskExecuteResult();

            task.setStatus(taskExecuteResult.getStatus());
            task.setMessage(taskExecuteResult.getMessage());
            task.setExtraJson(taskExecuteResult.getExtraJson());
            task.setSuccessCount(taskExecuteResult.getSuccessCount());
            task.setErrorCount(taskExecuteResult.getErrorCount());
            task.setFilePath(taskExecuteResult.getFilePath());
            task.setErrorRecordsFilePath(taskExecuteResult.getErrorRecordsFilePath());

            if(DataTransferTaskStatus.SUCCESS.getCode() == taskExecuteResult.getStatus()
                    && null != taskExecuteResult.getErrorCount() && taskExecuteResult.getErrorCount() > 0) {
                task.setStatus(DataTransferTaskStatus.PARTLY_ERROR.getCode());
            }

            if(DataTransferTaskStatus.FINISH_ERROR.getCode() == taskExecuteResult.getStatus()
                    || DataTransferTaskStatus.SUCCESS.getCode() == taskExecuteResult.getStatus() ) {
                task.setFinishedAt(new Date());
            }

            return Response.ok(dataTransferTaskPersistService.update(task));
        } catch (Exception e) {
            log.error("task.execute.callback.fail", e);
            return Response.fail("task.execute.callback.fail");
        }
    }

    @Override
    public Response<Boolean> deleteTaskFileById(DataTransferTaskDeleteRequest taskDeleteRequest) {
        var task = dataTransferTaskPersistService.findById(taskDeleteRequest.getTaskId());
        if(null == task || DataTransferTaskStatus.DELETE.equals(task.getStatus())) {
            return Response.ok(Boolean.TRUE);
        }

        if((!taskDeleteRequest.isAdmin()) && !Objects.equals(task.getUserId(), taskDeleteRequest.getUserId())) {
            return Response.fail(DataTransferErrorCode.TASK_USER_MISMATCH.getCode());
        }

        boolean isSuccess = false;
        String errorMessage = null;
        try {
            fileManager.deleteFile(task.getFilePath());
            fileManager.deleteFile(task.getErrorRecordsFilePath());
            isSuccess = true;
        } catch (Exception e) {
            log.error("deleteTaskFileById", e);
            errorMessage = DataTransferErrorCode.ERROR_DELETE_FILE.getCode();
            task.setMessage(e.getMessage());
        }
        if(isSuccess) {
            task.setStatus(DataTransferTaskStatus.DELETE.getCode());
        } else {
            task.setStatus(DataTransferTaskStatus.ERROR.getCode());
        }
        try {
            dataTransferTaskPersistService.update(task);
        } catch (Exception e) {
            isSuccess = false;
            if(StringUtils.isEmpty(errorMessage)) {
                errorMessage = DataTransferErrorCode.SERVER_ERROR.getCode();
            }
            log.error("deleteTaskFileById", e);
        }
        if(isSuccess) {
            return Response.ok(Boolean.TRUE);
        } else {
            return Response.fail(errorMessage);
        }
    }
}
