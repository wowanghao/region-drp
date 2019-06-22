package io.terminus.common.data.transfer.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2018/9/28
 *
 * @author yushuo
 */
public enum DataTransferTaskType implements Serializable {
    EXPORT(1, "导出"),
    IMPORT(2, "导入"),
    ;

    private int code;
    private String desc;


    private static final Map<Integer, DataTransferTaskType> codeMap = new HashMap<Integer, DataTransferTaskType>();

    static {
        for (DataTransferTaskType s : DataTransferTaskType.values()) {
            codeMap.put(s.getCode(), s);
        }
    }

    DataTransferTaskType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DataTransferTaskType fromCode(Integer code) {
        return codeMap.get(code);
    }
}
