package com.jiajiayue.all.regiondrp.common.model;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.Data;
import java.util.Map;

@Data
public class PageInfo {
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    private Integer offset;
    private Integer limit;

    public PageInfo(Integer pageNo, Integer size) {
        pageNo = (Integer) MoreObjects.firstNonNull(pageNo, 1);
        size = (Integer) MoreObjects.firstNonNull(size, 20);
        this.limit = size > 0 ? size : 20;
        this.offset = (pageNo - 1) * size;
        this.offset = this.offset > 0 ? this.offset : 0;
    }
    public Map<String, Object> toMap(String key1, String key2) {
        Map param = Maps.newHashMapWithExpectedSize(2);
        param.put(Strings.isNullOrEmpty(key1) ? "offset" : key1, this.offset);
        param.put(Strings.isNullOrEmpty(key2) ? "limit" : key2, this.limit);
        return param;
    }
}