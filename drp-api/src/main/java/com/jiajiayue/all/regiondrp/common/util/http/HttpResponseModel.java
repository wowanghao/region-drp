package com.jiajiayue.all.regiondrp.common.util.http;

import io.terminus.common.model.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author wh
 */
@Data
@Slf4j
public class HttpResponseModel<R> implements Serializable {

    private boolean success;

    private R result;

    private String error;

    public HttpResponseModel() {

    }

    public static <T> HttpResponseModel<T> ok(T data) {
        HttpResponseModel<T> resp = new HttpResponseModel(true);
        resp.setResult(data);
        return resp;
    }

    public static <T> HttpResponseModel<T> fail(String error) {
        HttpResponseModel<T> resp = new HttpResponseModel();
        resp.setError(error);
        return resp;
    }

    public HttpResponseModel(boolean success) {
        this.success = success;
    }

    public HttpResponseModel(Exception e) {
        this.success = false;
        this.error = e.getMessage();
    }

    public HttpResponseModel(String error) {
        this.success = false;
        this.error = error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

}
