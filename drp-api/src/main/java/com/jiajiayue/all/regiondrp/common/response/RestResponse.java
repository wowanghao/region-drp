package com.jiajiayue.all.regiondrp.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiajiayue.all.regiondrp.common.model.Paging;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author wh
 */
@Data
@Slf4j
public class RestResponse implements Serializable {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public RestResponse(boolean success) {
        this.success = success;
    }

    public RestResponse(Exception e) {
        this.success = false;
        this.error = e.getMessage();
    }

    public RestResponse(String error) {
        this.success = false;
        this.error = error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

}
