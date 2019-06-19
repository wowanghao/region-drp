package com.jiajiayue.all.regiondrp.aop;


import com.jiajiayue.all.regiondrp.common.exception.PlatformError;
import com.jiajiayue.all.regiondrp.common.exception.PlatformErrorEnum;
import com.jiajiayue.all.regiondrp.common.exception.PlatformException;
import io.jjy.platform.common.datasource.DynamicDataSource;
import io.jjy.platform.common.datasource.DynamicDataSourceContext;
import io.terminus.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * author
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Autowired
    private DynamicDataSourceContext dynamicDataSourceContext;

    @Autowired
    DynamicDataSource dynamicDataSource;

    @Around("execution( * com.jiajiayue.all.regiondrp.biz.service.impl.*.*(..))")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String stkId = request.getParameter("stkId");
        dynamicDataSourceContext.setDataSource(stkId);
        try {
            dynamicDataSource.getConnection();
        } catch (Exception ex) {
            throw new PlatformException(PlatformErrorEnum.BASIC_INFO_00010);
        }
        Object ret;
        try {
            ret = point.proceed();
        } catch (Exception ex) {
            throw ex;
        } finally {
            dynamicDataSourceContext.clear();
        }
        return ret;
    }

    public void doAfterReturning(JoinPoint joinPoint, Response response) {
        Signature signature = joinPoint.getSignature();
        log.info(" 进入日志切面：doAfterReturning :{}", signature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
    }

}