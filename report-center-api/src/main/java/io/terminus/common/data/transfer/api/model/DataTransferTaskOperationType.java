package io.terminus.common.data.transfer.api.model;

import io.terminus.api.consts.OperationType;

import java.io.Serializable;

/**
 * @author yushuo
 */
public enum DataTransferTaskOperationType implements OperationType, Serializable {

    DATA_IMPORT_TASK_CREATE("创建数据导入任务", true),
    DATA_EXPORT_TASK_CREATE("创建数据导出任务", true),

    DATA_TRANSFER_TASK_QUERY("数据导入/导出任务查询", false),
    DATA_TRANSFER_TASK_EXECUTE("执行数据导入/导出任务", true),
    DATA_TRANSFER_TASK_DELETE("删除数据导入/导出任务", true),
    ;

    private boolean isWrite;
    private String description;

    DataTransferTaskOperationType(String description, boolean isWrite) {
        this.description = description;
        this.isWrite = isWrite;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isWrite() {
        return isWrite;
    }

}
