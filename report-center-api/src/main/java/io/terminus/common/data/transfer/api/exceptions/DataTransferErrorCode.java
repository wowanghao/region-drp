package io.terminus.common.data.transfer.api.exceptions;

import io.terminus.api.consts.ErrorCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yushuo
 */

@Getter
public enum DataTransferErrorCode implements ErrorCode, Serializable {

    SERVER_ERROR("server.error", "未知错误"),
    USER_NOT_LOGIN("user.not.login", "用户未登录"),

    FILE_EXT_NAME_INVALID("file.ext.name.invalid", "不支持该文件类型"),
    ERROR_DELETE_FILE("task.file.delete.fail", "删除任务文件失败"),
    TASK_NOT_FOUND("task.not.found", "找不到对应的任务，请检查ID是否正确"),
    TASK_ID_IS_REQUIRED("task.id.is.required", "任务ID不能为空"),
    TASK_NAME_INVALID("task.name.invalid", "任务名称不正确"),
    TASK_USER_MISMATCH("task.user.mismatch", "导入/导出任务不是您创建的"),
    TASK_TYPE_INVALID("task.type.invalid", "任务类型不正确"),
    TASK_HAS_NO_PROVIDER("task.has.no.provider", "任务没有注册，请检查任务名, 以及对应的后端服务是否正确"),
    TASK_EXECUTE_INTERVAL_LIMIT("task.execute.interval.limit", "请勿频繁执行任务"),
    IMPORT_TASK_FILE_INVALID("import.task.file.invalid", "导入任务提供的文件无效"),

    ;

    private String code;
    private String message;

    private static Map<String, DataTransferErrorCode> codeMap = new HashMap<>();

    DataTransferErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    static {
        for (DataTransferErrorCode errorCode : DataTransferErrorCode.values()) {
            codeMap.put(errorCode.getCode(), errorCode);
        }
    }

    public static DataTransferErrorCode resolve(String code) {
        return codeMap.get(code);
    }

}
