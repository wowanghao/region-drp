package com.jiajiayue.all.regiondrp.common.request;

import com.jiajiayue.all.regiondrp.common.model.PageInfo;
import com.jiajiayue.all.regiondrp.common.util.ParamUtil;
import io.swagger.annotations.ApiModelProperty;
import io.terminus.common.model.Criteria;
import lombok.Data;

@Data
public abstract class AbstractPageRequest extends AbstractRequest {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int MAX_PAGE_SIZE = 1024;

    @ApiModelProperty(value = "当前页码-默认1，从1开始", required = true)
    private int pageNo = 1;
    @ApiModelProperty(value = "每页条数-默认20，最大1024", required = true)
    private int pageSize = 20;
    @ApiModelProperty(hidden = true)
    private int limit = 20;
    @ApiModelProperty(hidden = true)
    private int offset = 0;
    @ApiModelProperty(hidden = true)
    private String sort;

    @Override
    public void checkParam() {
        ParamUtil.expectTrue(this.pageNo >= 1, String.format("Invalid pageNo = %d, it should >= %d", this.pageNo, 1));
        ParamUtil.expectInRange(this.pageSize, 1, 1024, String.format("Invalid pageSize = %d, it should in range:[%d, %d]", this.pageSize, 1, 1024));
        initLimitOffset();
    }

    public void initLimitOffset() {
        limit = getLimit();
        offset = getOffset();
    }

    public Integer getLimit() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getLimit();
    }

    public Integer getOffset() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getOffset();
    }
}