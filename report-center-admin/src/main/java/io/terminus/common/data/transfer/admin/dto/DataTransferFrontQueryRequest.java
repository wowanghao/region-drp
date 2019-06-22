package io.terminus.common.data.transfer.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yushuo
 */
@Data
@ApiModel
public class DataTransferFrontQueryRequest implements Serializable {

    @ApiModelProperty("导入/导入任务的唯一名称")
    private String name;

    @ApiModelProperty("任务的展示名称，部分匹配")
    private String displayName;

    @ApiModelProperty("导入/导出：导入=2，导出=1")
    private Integer taskType;

    @ApiModelProperty("-1: 失败, 0: 等待, 1: 正在执行, 2: 已完成, 3: 已删除")
    private Integer taskStatus;

    @ApiModelProperty("创建任务的用户名")
    private String userName;

    @ApiModelProperty("创建任务的用户ID")
    private Long userId;

    @ApiModelProperty("操作时间开始")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date createTimeStart;

    @ApiModelProperty("操作时间结束")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date createTimeEnd;

    private Integer pageNo = 1;

    private Integer pageSize = 20;

}
