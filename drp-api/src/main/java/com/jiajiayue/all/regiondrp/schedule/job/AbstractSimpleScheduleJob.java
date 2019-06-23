/*
package com.jiajiayue.all.regiondrp.schedule.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.jiajiayue.all.regiondrp.schedule.model.JobProcessParam;
import com.jiajiayue.all.regiondrp.schedule.util.BaseJobEventUtils;
import io.jjy.platform.common.event.DrpScheduleExecutionEvent;
import io.terminus.common.redis.utils.JedisTemplate;
import io.terminus.common.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

*/
/**
 * @author WangHao
 * @date 2019/5/26 2:09
 *//*

public abstract class AbstractSimpleScheduleJob extends AbstractJob implements SimpleJob {
    private static final Logger log = LoggerFactory.getLogger(AbstractSimpleScheduleJob.class);
    protected static JsonMapper objectMapper;
    @Autowired
    protected JedisTemplate jedisTemplate;

    public AbstractSimpleScheduleJob() {
    }

    public void execute(ShardingContext shardingContext) {
        log.info(shardingContext.getJobName() + ">>>" + shardingContext.getShardingItem() + " is starting");
        Date startedAt = new Date();
        DrpScheduleExecutionEvent drpScheduleExecutionEvent = from(shardingContext, startedAt);

        try {
            String key = getKey(shardingContext);
            Date lastStartAt = (Date)this.jedisTemplate.execute((jedis) -> {
                String timeString = jedis.get(key);
                return Strings.isNullOrEmpty(timeString) ? null : new Date(Long.valueOf(timeString));
            });
            this.jedisTemplate.execute((jedis) -> {
                jedis.set(key, startedAt.getTime() + "");
            });
            JobProcessParam jobProcessParam = JobProcessParam.from(shardingContext, startedAt, lastStartAt);
            this.execute(jobProcessParam);
            super.sendLog("db_schedule_execute_log", drpScheduleExecutionEvent.success());
        } catch (Throwable var10) {
            log.error(shardingContext.getJobName() + ">>>" + shardingContext.getShardingItem() + " execute fail, cause {}", Throwables.getStackTraceAsString(var10));
            super.sendLog("db_schedule_execute_log", drpScheduleExecutionEvent.fail(var10.getMessage()));
        } finally {
            log.info(shardingContext.getJobName() + ">>>" + shardingContext.getShardingItem() + " is ending");
        }

    }

    protected static DrpScheduleExecutionEvent from(ShardingContext shardingContext, Date startedAt) {
        DrpScheduleExecutionEvent dbScheduleExecuteLogEvent = new DrpScheduleExecutionEvent();
        BaseJobEventUtils.setUp(dbScheduleExecuteLogEvent, shardingContext);
        dbScheduleExecuteLogEvent.setStartedAt(startedAt);
        return dbScheduleExecuteLogEvent;
    }

    protected static String getKey(ShardingContext shardingContext) {
        return "JJY_JOB_DRP_" + shardingContext.getJobName() + "_" + shardingContext.getShardingItem();
    }

    protected abstract void execute(JobProcessParam var1) throws Exception;

    static {
        objectMapper = JsonMapper.JSON_NON_EMPTY_MAPPER;
    }
}
*/
