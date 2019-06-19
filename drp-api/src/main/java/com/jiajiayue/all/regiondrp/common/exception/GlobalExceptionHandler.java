package com.jiajiayue.all.regiondrp.common.exception;

import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import io.terminus.common.model.Response;
import io.terminus.common.rocketmq.core.TerminusMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * wh
 */
@Slf4j
@ControllerAdvice
class GlobalExceptionHandler {

    @Autowired
    private TerminusMQProducer terminusMQProducer;


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResponse exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        RestResponse response = new RestResponse(e);
        return response;
    }


    @ResponseBody
    @ExceptionHandler(value = PlatformException.class)
    public RestResponse PlatformExceptionHandler(PlatformException e) {
        RestResponse response = new RestResponse(e);
        return response;
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public RestResponse exceptionHandler(BindException e) {
        log.error(e.getBindingResult().getFieldError().getDefaultMessage());
        FieldError fd = (FieldError) e.getBindingResult().getAllErrors().get(0);
        RestResponse response = new RestResponse(fd.getField() + ": 格式错误");
        return response;
    }

}
