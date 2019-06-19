package com.jiajiayue.all.regiondrp.biz.dto.request;

import com.jiajiayue.all.regiondrp.common.request.AbstractPageRequest;
import com.jiajiayue.all.regiondrp.common.util.ConvertUtil;
import com.jiajiayue.all.regiondrp.common.util.ParamUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author WangHao
 * @date 2019/5/25 18:36
 */
@Data
public class BasicInfoRequest extends AbstractPageRequest {
    @ApiModelProperty(value = "门店编码", required = true)
    String stkId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @ApiModelProperty(value = "上次更新时间(格式：yyyy-MM-dd HH:mm:ss.SSS)")
    Date lastUpdateTime;

    @Override
    public void checkParam() {
        super.checkParam();
        ParamUtil.nonNull(this.stkId, "stkId.can.not.be.null");
        //ParamUtil.nonNull(this.lastUpdateTime, "lastUpdateTime.can.not.be.null");
    }
}
