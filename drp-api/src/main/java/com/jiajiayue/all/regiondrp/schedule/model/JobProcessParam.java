package com.jiajiayue.all.regiondrp.schedule.model;

import com.dangdang.ddframe.job.api.ShardingContext;
import lombok.Data;

import java.util.Date;

@Data
public class JobProcessParam {
    private ShardingContext shardingContext;
    private Date startAt;
    private Date lastStartAt;

    public static JobProcessParam from(ShardingContext shardingContext, Date startAt, Date lastStartAt) {
        JobProcessParam jobProcessParam = new JobProcessParam();
        jobProcessParam.setShardingContext(shardingContext);
        jobProcessParam.setStartAt(startAt);
        jobProcessParam.setLastStartAt(lastStartAt);
        return jobProcessParam;
    }
}