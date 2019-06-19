package com.jiajiayue.all.regiondrp.aop;


import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.dto.request.MockRequest;
import com.jiajiayue.all.regiondrp.common.request.AbstractPageRequest;
import com.jiajiayue.all.regiondrp.common.request.AbstractRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import io.jjy.platform.common.constant.TopicEnum;
import io.jjy.platform.common.event.OpenPlatformExecutionEvent;
import io.terminus.common.rocketmq.core.TerminusMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * author wh
 */
@Aspect
@Component
@Slf4j
public class SecurityAspect {

    @Autowired
    private TerminusMQProducer terminusMQProducer;

    @Around("execution( * com.jiajiayue.all.regiondrp.biz.controller.*.*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        Object ret;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            Object[] args = point.getArgs();
            if (args != null && args.length > 0 && args[0].getClass().getSuperclass().getSuperclass() == AbstractRequest.class) {
                AbstractRequest abstractRequest = (AbstractRequest) args[0];
                abstractRequest.checkParam();
            }
            ret = point.proceed();
            this.sendMq(request, ret);
        } catch (Throwable e) {
            this.sendMq(request, e);
            throw e;
        }
        return ret;
    }

    private void sendMq(HttpServletRequest request, Object obj) {
        this.sendMq(request, obj, null);
    }

    private void sendMq(HttpServletRequest request, Object obj, Throwable e) {
        try {
            OpenPlatformExecutionEvent openPlatformExecutionEvent = new OpenPlatformExecutionEvent();
            openPlatformExecutionEvent.setExecutionAt(new Date());
            openPlatformExecutionEvent.setSource("drp-api-store");
            if (e == null) {
                if (obj instanceof ResponseEntity) {
                    ResponseEntity res = (ResponseEntity) obj;
                    openPlatformExecutionEvent.setMessage(res.getBody().toString());
                }
            } else {
                openPlatformExecutionEvent.setMessage(e.getMessage());
            }
            String executeUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                executeUrl += "?" + queryString;
            }
            openPlatformExecutionEvent.setRequestUrl(executeUrl);
            openPlatformExecutionEvent.setExecutionState(e == null ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            openPlatformExecutionEvent.setRequestBody(getRequestData(request));
            terminusMQProducer.send(TopicEnum.SERVER_EXECUTE_LOG_TOPIC, openPlatformExecutionEvent);
        } catch (Exception ex) {
            log.error("PlatformException openPlatformExecutionEvent error{}", ex);
        }
    }

    private String getRequestData(final HttpServletRequest request) throws UnsupportedEncodingException {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
            }
        }
        return payload;
    }
}