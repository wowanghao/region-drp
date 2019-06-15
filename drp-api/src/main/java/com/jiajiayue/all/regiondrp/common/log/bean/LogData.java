package com.jiajiayue.all.regiondrp.common.log.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangHao
 * @date 2019/6/5 11:57
 */
@Data
@Builder
public class LogData implements Serializable {
    private Long id;
    private String module;
    private String description;
    private String methodKey;
    private Integer type;
    private String primaryKey;
    private String requestJson;
    private String responseJson;
    private Date createdAt;
}