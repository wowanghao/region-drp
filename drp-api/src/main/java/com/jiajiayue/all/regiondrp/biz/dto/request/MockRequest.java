package com.jiajiayue.all.regiondrp.biz.dto.request;

import com.jiajiayue.all.regiondrp.common.request.AbstractPageRequest;
import com.jiajiayue.all.regiondrp.common.util.ParamUtil;
import lombok.Data;

/**
 * @author WangHao
 * @date 2019/5/25 18:36
 */
@Data
public class MockRequest extends AbstractPageRequest {

    String stkId;

    Long lastModify;

    @Override
    public void checkParam() {
        super.checkParam();
        ParamUtil.nonNull(this.stkId, "stkId.can.not.be.null");
        ParamUtil.nonNull(this.lastModify, "lastModify.can.not.be.null");
        ParamUtil.expectTrue(this.lastModify >= 0L, String.format("Invalid timeStamp = %d, it should >= 0", this.lastModify, 0L));
    }
}
