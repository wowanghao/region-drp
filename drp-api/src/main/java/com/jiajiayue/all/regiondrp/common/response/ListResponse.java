package com.jiajiayue.all.regiondrp.common.response;

import lombok.Data;
import lombok.var;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yushuo
 */
@Data
public class ListResponse implements Serializable {

    private boolean success;

    private String error;

    private List<Map<String, Object>> errorLines;

    public ListResponse(boolean success) {
        this.success = success;
    }

    public static ListResponse ok() {
        return new ListResponse(true);
    }

    public static ListResponse fail(String error) {
        var ret = new ListResponse(false);
        ret.setError(error);
        return ret;
    }

}
