package io.terminus.common.data.transfer.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2018/9/28
 *
 * @author yushuo
 */
public enum DataTransferTaskStatus implements Serializable {
    CANCEL(-100, "已取消"),
    PARTLY_ERROR(-5, "部分数据导入出错"),
    DELETE_ERROR(-4, "删除时发生错误"),
    FINISH_ERROR(-3, "执行结束回调失败"),
    TIMEOUT(-2, "执行超时"),
    ERROR(-1, "失败"),
    PENDING(0, "等待中"),
    RUNNING(1, "执行中"),
    SUCCESS(2, "已完成"),
    DELETE(3, "已删除"),
    ;

    private int code;
    private String desc;


    private static final Map<Integer, DataTransferTaskStatus> codeMap = new HashMap<>();

    static {
        for (DataTransferTaskStatus s : DataTransferTaskStatus.values()) {
            codeMap.put(s.getCode(), s);
        }
    }

    DataTransferTaskStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DataTransferTaskStatus fromCode(Integer code) {
        return codeMap.get(code);
    }
}
