package com.jiajiayue.all.regiondrp.common.log.service.impl;


import com.jiajiayue.all.regiondrp.common.log.bean.LogData;
import com.jiajiayue.all.regiondrp.common.log.dao.LogDao;
import com.jiajiayue.all.regiondrp.common.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangHao
 * @date 2019/6/13 11:29
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public Boolean create(LogData logData) {
        return logDao.create(logData);
    }
}
