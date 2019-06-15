package com.jiajiayue.all.regiondrp.biz.dto.wrapper;

import com.jiajiayue.all.regiondrp.biz.dto.line.StockLine;
import com.jiajiayue.all.regiondrp.biz.dto.request.MockRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wh
 */
@Data
public class LineWrapper {

    /**
     * 传入的库存行
     */
    private final StockLine stockLine;

    /**
     * 状态
     */
    @Getter
    @Setter
    private Status status;

    public LineWrapper(StockLine stockLine) {
        this.stockLine = stockLine;
        this.status = Status.OK;
    }
    public enum Status {
        OK(0, null, null),
        OVERSELL(-1, "quantity.not.enough", "库存不足, 可能超售了"),
        OUTDATED(-90, "tick.time.outdated", "数据过时了"),
        ERROR(-100, "server.error", "错误"),
        STORE_NOT_FOUND(-101, "store.not.found", "门店/仓库不存在"),
        SKU_NOT_FOUND(-102, "sku.not.found", "商品不存在"),
        ;
        @Getter
        Integer code;

        @Getter
        String errorCode;

        @Getter
        String errorMessage;

        Status(Integer code, String errorCode, String errorMessage) {
            this.code = code;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

    }

}
