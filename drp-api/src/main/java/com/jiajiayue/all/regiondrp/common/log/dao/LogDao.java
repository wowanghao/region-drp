package com.jiajiayue.all.regiondrp.common.log.dao;

import com.jiajiayue.all.regiondrp.common.log.bean.LogData;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

/**
 * @author WangHao
 * @date 2019/6/13 11:32
 */
@Repository
public class LogDao extends MyBatisDao<LogData> {
}
