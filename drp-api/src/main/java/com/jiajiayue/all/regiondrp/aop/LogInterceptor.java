package com.jiajiayue.all.regiondrp.aop;

import com.alibaba.fastjson.JSON;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.log.bean.LogData;
import com.jiajiayue.all.regiondrp.common.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @author WangHao
 * @date 2019/6/4 15:56
 */
@Component
@Aspect
@Slf4j
public class LogInterceptor {

    @Autowired
    LogService logService;

    @Pointcut("@annotation(com.jiajiayue.all.regiondrp.common.annotation.LogMe)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        this.inputLog(joinPoint);
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodKey = this.getMethodKey(method);
        LogMe logme = method.getAnnotation(LogMe.class);

        Object returnObject = joinPoint.proceed(joinPoint.getArgs());

        LogData logData = LogData.builder().module(logme.module())
                .description(logme.description()).methodKey(methodKey)
                .requestJson(JSON.toJSONString(joinPoint.getArgs()))
                .responseJson(JSON.toJSONString(returnObject))
                .createdAt(new Date()).build();
        log.info(log.toString());
        logService.create(logData);
        return returnObject;
    }

    private void inputLog(ProceedingJoinPoint joinPoint) {
        log.info("applog method : " + joinPoint.getSignature().getName());
        if (log.isDebugEnabled()) {
            log.debug("LogAround() is running!");
            log.debug("Hijacked method : " + joinPoint.getSignature().getName());
            log.debug("Hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
            log.debug("Around before is running!");
        }

    }

    public String getMethodKey(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append('.');
        sb.append(method.getName());
        sb.append(getMethodDescriptor(method));
        return sb.toString();
    }

    public String getMethodDescriptor(Method m) {
        Class<?>[] parameters = m.getParameterTypes();
        StringBuilder buf = new StringBuilder();
        buf.append('(');

        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }

        buf.append(')');
        getDescriptor(buf, m.getReturnType());
        return buf.toString();
    }

    private void getDescriptor(StringBuilder buf, Class<?> c) {
        Class d;
        for (d = c; !d.isPrimitive(); d = d.getComponentType()) {
            if (!d.isArray()) {
                buf.append('L');
                String name = d.getName();
                int len = name.length();

                for (int i = 0; i < len; ++i) {
                    char car = name.charAt(i);
                    buf.append(car == '.' ? '/' : car);
                }

                buf.append(';');
                return;
            }

            buf.append('[');
        }

        char car;
        if (d == Integer.TYPE) {
            car = 'I';
        } else if (d == Void.TYPE) {
            car = 'V';
        } else if (d == Boolean.TYPE) {
            car = 'Z';
        } else if (d == Byte.TYPE) {
            car = 'B';
        } else if (d == Character.TYPE) {
            car = 'C';
        } else if (d == Short.TYPE) {
            car = 'S';
        } else if (d == Double.TYPE) {
            car = 'D';
        } else if (d == Float.TYPE) {
            car = 'F';
        } else {
            car = 'J';
        }

        buf.append(car);
    }
}
