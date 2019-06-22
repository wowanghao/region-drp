package io.terminus.common.data.transfer.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.terminus.common.data.transfer.api.model.FileType;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * @author yushuo
 */
@Data
@ApiModel
public class DataTransferFrontTaskCreateRequest implements Serializable {

    @ApiModelProperty("导入/导入任务的唯一名称")
    private String name;

    @ApiModelProperty("任务的展示名称，部分匹配")
    private String displayName;

    @ApiModelProperty("任务的简要描述")
    private String description;

    @ApiModelProperty("导出文件的扩展名")
    private String extName = FileType.XLS.getExtName();

    @ApiModelProperty("导入文件的文件fileKey")
    private String filePath;

    @JsonRawValue
    @ApiModelProperty("任务的执行参数，各模块任务定义")
    private String criteria;

    @JsonProperty(value = "criteria")
    public void setCriteriaJson(JsonNode jsonNode) throws IOException {
        // https://stackoverflow.com/questions/4783421/how-can-i-include-raw-json-in-an-object-using-jackson
        StringWriter stringWriter = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try(JsonGenerator generator = new JsonFactory(objectMapper).createGenerator(stringWriter)) {
            generator.writeTree(jsonNode);
        }
        setCriteria(stringWriter.toString());
    }

}
