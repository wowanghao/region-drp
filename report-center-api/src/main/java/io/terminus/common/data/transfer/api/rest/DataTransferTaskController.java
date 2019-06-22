package io.terminus.common.data.transfer.api.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.api.model.FileType;
import io.terminus.common.data.transfer.api.service.facade.IDataTransferService;
import io.terminus.common.model.Paging;
import io.terminus.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Slf4j
@Deprecated
public class DataTransferTaskController {

    @Autowired
    private IDataTransferService dataTransferService;

    private final ObjectMapper objectMapper;

    public DataTransferTaskController(IDataTransferService dataTransferService) {
        this.dataTransferService = dataTransferService;
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @PutMapping("/task/export/create")
    public Response<DataTransferTask> createDataExportTask(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "desc", required = false) String desc,
            @RequestParam(name = "ext", required = false) String ext,
            @RequestBody String criteriaJson
    ) {
        try {
            FileType fileType = getFileType(ext);
            if(null == fileType) {
                return Response.fail("Unsupported filetype");
            }

            return dataTransferService.createExportTask(name, fileType, desc, criteriaJson);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/task/export/run")
    public Response<DataTransferTask> runDataExportTask(@RequestParam(name = "id") Long id) {
        try {
            return dataTransferService.runTaskById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/task/delete")
    public Response<Boolean> deleteTaskFile(@RequestParam(name = "id") Long id) {
        try {
            return dataTransferService.deleteTaskFile(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/task/download")
    public Object downloadTaskOutput(@RequestParam(name = "id") Long id) {
        try {
            Response<String> rUrl = dataTransferService.getTaskFileUrl(id);
            if(rUrl.isSuccess() && StringUtils.isNotEmpty(rUrl.getResult())) {
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create(rUrl.getResult()));
                return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
            }
            return rUrl;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }


    @GetMapping("/task/paging")
    public Response<Paging> listDataExportTask(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer size,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "name", required = false) String name
    ) {
        try {
            Map<String, Object> criteria = new HashMap<>();
            if(null != type) {
                criteria.put("type", type);
            }
            if(null != name) {
                criteria.put("name", name);
            }
            var rPage = dataTransferService.listTasks(page, size, criteria);
            if(!rPage.isSuccess()) {
                return Response.fail(rPage.getError());
            }
            if(rPage.getResult().isEmpty()) {
                return Response.ok(Paging.empty());
            }
            var ret = rPage.getResult().getData().stream().map(t -> {
                Map<String, Object> item = objectMapper.convertValue(t, Map.class);
                if(StringUtils.isNotEmpty(t.getFilePath())) {
                    String fileName = Paths.get(t.getFilePath()).getFileName().toString();
                    item.put("fileName", fileName);
                }
                return item;
            }).collect(Collectors.toList());
            return Response.ok(new Paging<>(rPage.getResult().getTotal(), ret));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/task/import/create")
    public Response<DataTransferTask> createDataExportTask(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "desc") String desc,
            @RequestParam(name = "filePath") String filePath){
        try {
            String[] ext = filePath.split("\\.");
            CollectionUtils.reverseArray(ext);
            FileType fileType = getFileType(ext[0]);
            if(null == fileType) {
                return Response.fail("Unsupported filetype");
            }
            URL url = new URL(filePath);
            String path = url.getPath();
            if ("http".equals(url.getProtocol())) {
                path = url.getPath().replaceFirst("/","");
            }
            return dataTransferService.createImportTask(name, desc, path, fileType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.fail(e.getMessage());
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
