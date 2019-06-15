package com.jiajiayue.all.regiondrp.schedule.constant;

import io.jjy.platform.common.constant.ExecutionConstants;

public class JobConstants {
    public static final String JOB_INSTANCE_ID;

    public JobConstants() {
    }

    static {
        JOB_INSTANCE_ID = ExecutionConstants.EXECUTION_IP + "@-@" + ExecutionConstants.EXECUTION_PID;
    }
}
