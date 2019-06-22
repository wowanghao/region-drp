package io.terminus.common.data.transfer.api.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */
public enum FileType implements Serializable {
    XLS("xlsx"),
//    CSV("csv"),
    // 公司的导入时，会将xlsx文件改后缀名，将xlsx改为sheet。因此需要添加一个`sheet`类型的检查
    SHEET("sheet");

    private String extName;

    private static final Map<String, FileType> extNameMap = new HashMap<String, FileType>();

    static {
        for (FileType ft : FileType.values()) {
            extNameMap.put(ft.getExtName(), ft);
        }
    }

    FileType(String extName) {
        this.extName = extName.toLowerCase();
    }

    public String getExtName() {
        return extName;
    }

    public static FileType fromExtName(String extName) {
        if(StringUtils.isEmpty(extName)) {
            return null;
        }
        return extNameMap.get(extName.toLowerCase());
    }

}
