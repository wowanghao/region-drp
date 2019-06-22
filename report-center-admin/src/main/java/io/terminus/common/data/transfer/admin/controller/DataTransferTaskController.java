package io.terminus.common.data.transfer.admin.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.terminus.common.data.transfer.admin.dto.DataTransferFrontQueryRequest;
import io.terminus.common.data.transfer.admin.dto.DataTransferFrontTaskCreateRequest;
import io.terminus.common.data.transfer.admin.spi.IReportCenterAclProvider;
import io.terminus.common.data.transfer.api.exceptions.DataTransferErrorCode;
import io.terminus.common.data.transfer.api.model.*;
import io.terminus.common.data.transfer.api.facade.IDataTransferTaskManageFacade;
import io.terminus.common.data.transfer.api.service.dto.*;
import io.terminus.common.model.Paging;
import io.terminus.parana.common.web.context.RequestContext;
import io.terminus.parana.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
@Slf4j
@RestController
public class DataTransferTaskController {

    private final IDataTransferTaskManageFacade dataTransferTaskManageFacade;

    private final IReportCenterAclProvider reportCenterAclProvider;

    private final ObjectMapper objectMapper;

    public DataTransferTaskController(
            IDataTransferTaskManageFacade dataTransferTaskManageFacade,
            IReportCenterAclProvider reportCenterAclProvider) {
        this.dataTransferTaskManageFacade = dataTransferTaskManageFacade;
        this.reportCenterAclProvider = reportCenterAclProvider;
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @PostMapping("/task/export/create/{name}")
    public DataTransferTask createDataExportTask(
            @PathVariable(name = "name") String name,
            @RequestBody DataTransferFrontTaskCreateRequest request) {
        //判断权限
        FileType fileType = getFileType(request.getExtName());
        if (null == fileType) {
            throw new RestException(DataTransferErrorCode.FILE_EXT_NAME_INVALID.getCode());
        }

        var taskCreateReq = new DataTransferTaskCreateRequest();
        taskCreateReq.setTaskType(DataTransferTaskType.EXPORT.getCode());
        taskCreateReq.setName(name);
        taskCreateReq.setDisplayName(request.getDisplayName());
        taskCreateReq.setCriteriaJson(StringUtils.isEmpty(request.getCriteria()) ? "{}" : request.getCriteria());
        taskCreateReq.setDesc(request.getDescription());
        taskCreateReq.setUserId(123456L);
        taskCreateReq.setUserName("test");
        taskCreateReq.setUserNickname("test");
        taskCreateReq.setFileExt(fileType.getExtName());

        try {
            taskCreateReq.checkParam();
        } catch (IllegalArgumentException e) {
            log.error("task create check param fail", e);
            throw new RestException(e.getMessage());
        }
        var resp = dataTransferTaskManageFacade.createTask(taskCreateReq);
        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }


    /**
     * @param name 任务名，与importer/exporter对应
     * @return DataTransferTask
     */
    @PostMapping("/task/import/create/{name}")
    public DataTransferTask createDataImportTask(
            @PathVariable(name = "name") String name,
            @RequestBody DataTransferFrontTaskCreateRequest request
    ) {
        String extName = FilenameUtils.getExtension(request.getFilePath());
        FileType fileType = getFileType(extName);

        Long userId = RequestContext.getUserId();
        String userName = RequestContext.getUserName();
        String userNickname = RequestContext.getNickName();

        if (null == userId) {
            throw new RestException(HttpStatus.UNAUTHORIZED, DataTransferErrorCode.USER_NOT_LOGIN.getCode());
        }

        if (null == fileType) {
            throw new RestException(DataTransferErrorCode.FILE_EXT_NAME_INVALID.getCode());
        }

        var taskCreateReq = new DataTransferTaskCreateRequest();

        taskCreateReq.setTaskType(DataTransferTaskType.IMPORT.getCode());
        taskCreateReq.setName(name);
        taskCreateReq.setDisplayName(request.getDisplayName());
        taskCreateReq.setDesc(request.getDescription());
        taskCreateReq.setUserId(userId);
        taskCreateReq.setUserName(userName);
        taskCreateReq.setUserNickname(userNickname);
        taskCreateReq.setFilePath(request.getFilePath());
        taskCreateReq.setFileExt(fileType.getExtName());

        try {
            taskCreateReq.checkParam();
        } catch (IllegalArgumentException e) {
            log.error("task create check param fail", e);
            throw new RestException(e.getMessage());
        }
        var resp = dataTransferTaskManageFacade.createTask(taskCreateReq);
        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }

    @PostMapping("/task/paging")
    public Paging<DataTransferTask> pagingTasks(@RequestBody DataTransferFrontQueryRequest req) {
        Long userId = RequestContext.getUserId();
        String userName = RequestContext.getUserName();
        if (null == userId) {
            throw new RestException(HttpStatus.UNAUTHORIZED, DataTransferErrorCode.USER_NOT_LOGIN.getCode());
        }

        var taskPagingReq = new DataTransferTaskPagingRequest();

        taskPagingReq.setName(req.getName());
        taskPagingReq.setDisplayName(req.getDisplayName());
        taskPagingReq.setTaskType(req.getTaskType());
        taskPagingReq.setFrontStatus(req.getTaskStatus());
        taskPagingReq.setPageNo(req.getPageNo());
        taskPagingReq.setPageSize(req.getPageSize());
        taskPagingReq.setCreateTimeStart(req.getCreateTimeStart());
        taskPagingReq.setCreateTimeEnd(req.getCreateTimeEnd());

        if (userIsAdmin(userId)) {
            taskPagingReq.setUserId(req.getUserId());
            taskPagingReq.setUserName(req.getUserName());
            taskPagingReq.setAdmin(true);
        } else {
            taskPagingReq.setUserId(userId);
            taskPagingReq.setUserName(userName);
        }

        var resp = dataTransferTaskManageFacade.pagingTasks(taskPagingReq);

        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }

    private boolean userIsAdmin(Long userId) {
        return reportCenterAclProvider.userIsAdmin(userId);
    }

    @GetMapping("/task/execute")
    public DataTransferTask executeDataTransferTask(@RequestParam(name = "id") Long id) {
        Long userId = RequestContext.getUserId();
        String userName = RequestContext.getUserName();
        if (null == userId) {
            throw new RestException(HttpStatus.UNAUTHORIZED, DataTransferErrorCode.USER_NOT_LOGIN.getCode());
        }

        var taskExecuteReq = new DataTransferTaskExecuteRequest();
        taskExecuteReq.setTaskId(id);
        taskExecuteReq.setUserId(userId);
        taskExecuteReq.setUserName(userName);
        taskExecuteReq.setAdmin(userIsAdmin(userId));

        var resp = dataTransferTaskManageFacade.executeTaskById(taskExecuteReq);

        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }

    @DeleteMapping("/task/delete")
    public Boolean deleteDataTransferTaskFile(@RequestParam(name = "id") Long id) {
        Long userId = RequestContext.getUserId();
        String userName = RequestContext.getUserName();
        if (null == userId) {
            throw new RestException(HttpStatus.UNAUTHORIZED, DataTransferErrorCode.USER_NOT_LOGIN.getCode());
        }

        var taskDeleteReq = new DataTransferTaskDeleteRequest();
        taskDeleteReq.setTaskId(id);
        taskDeleteReq.setUserId(userId);
        taskDeleteReq.setUserName(userName);
        taskDeleteReq.setAdmin(userIsAdmin(userId));

        var resp = dataTransferTaskManageFacade.deleteTaskFileById(taskDeleteReq);

        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }

    @GetMapping("/task/result")
    public DataTransferTaskResult queryDataTransferTaskResult(@RequestParam(name = "id") Long id) {
        Long userId = RequestContext.getUserId();
        String userName = RequestContext.getUserName();
        if (null == userId) {
            throw new RestException(HttpStatus.UNAUTHORIZED, DataTransferErrorCode.USER_NOT_LOGIN.getCode());
        }

        var taskQueryReq = new DataTransferTaskQueryRequest();
        taskQueryReq.setTaskId(id);
        taskQueryReq.setUserId(userId);
        taskQueryReq.setUserName(userName);
        taskQueryReq.setAdmin(userIsAdmin(userId));
        var resp = dataTransferTaskManageFacade.findTaskResultById(taskQueryReq);

        if (resp.isSuccess()) {
            return resp.getResult();
        } else {
            throw new RestException(resp.getError());
        }
    }

    private FileType getFileType(String ext) {
        FileType fileType = FileType.XLS;
        if (StringUtils.isNotEmpty(ext)) {
            fileType = FileType.fromExtName(ext);
        }
        return fileType;
    }

}
