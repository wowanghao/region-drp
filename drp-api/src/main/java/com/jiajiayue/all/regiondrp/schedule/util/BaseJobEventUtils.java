/*
package com.jiajiayue.all.regiondrp.schedule.util;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.jiajiayue.all.regiondrp.schedule.constant.JobConstants;
import io.jjy.platform.common.event.BaseJobEvent;

public class BaseJobEventUtils {
    public BaseJobEventUtils() {
    }

    public static void setUp(BaseJobEvent baseJobEvent, ShardingContext shardingContext) {
        baseJobEvent.setJobInstanceId(JobConstants.JOB_INSTANCE_ID);
        baseJobEvent.setJobName(shardingContext.getJobName());
        baseJobEvent.setJobParameter(shardingContext.getJobParameter());
        baseJobEvent.setShardingItem(shardingContext.getShardingItem());
        baseJobEvent.setShardingParameter(shardingContext.getShardingParameter());
        baseJobEvent.setTaskId(shardingContext.getTaskId());
    }
}
*/
