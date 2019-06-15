/*
 * Copyright (c) 2018. 杭州端点网络科技有限公司.  All rights reserved.
 */
package com.jiajiayue.all.regiondrp.common;

import lombok.extern.slf4j.Slf4j;

/**
 * Author: monster
 * Date: 2018/12/29
 */
@Slf4j
public class ThreadCache {
    private static final ThreadLocal<ThreadContext> cache = new ThreadLocal<ThreadContext>() {
        @Override
        protected ThreadContext initialValue() {
            return new ThreadContext();
        }
    };

    private static class ThreadContext {
        String requestPath;
        String method;
        String queryParam;
        String contentType;
        String requestData;
    }

    public static void setRequestPath(String requestPath) {
        cache.get().requestPath = requestPath;
    }

    public static String getRequestPath() {
        return cache.get().requestPath;
    }

    public static void setMethod(String method) {
        cache.get().method = method;
    }

    public static String getMethod() {
        return cache.get().method;
    }

    public static void setQueryParam(String queryParam) {
        cache.get().queryParam = queryParam;
    }

    public static String getQueryParam() {
        return cache.get().queryParam;
    }

    public static void setContentType(String contentType) {
        cache.get().contentType = contentType;
    }

    public static String getContentType() {
        return cache.get().contentType;
    }

    public static void setRequestData(String requestData) {
        cache.get().requestData = requestData;
    }

    public static String getRequestData() {
        return cache.get().requestData;
    }

    public static void release() {
        cache.remove();
    }


}
