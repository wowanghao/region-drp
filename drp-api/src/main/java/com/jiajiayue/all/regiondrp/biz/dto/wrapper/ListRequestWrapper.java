package com.jiajiayue.all.regiondrp.biz.dto.wrapper;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author yushuo
 */
public class ListRequestWrapper {


    /**
     * (storeCode, skuCode) -> lineContext
     */
    private final LinkedHashMap<Pair<String, String>, LineWrapper> LineWrappers = new LinkedHashMap<>();


    public void putLineWrapper(LineWrapper lineContext) {
        this.LineWrappers.put(Pair.of(
                lineContext.getStockLine().getStoreCode(), lineContext.getStockLine().getSkuCode()),
                lineContext
        );
    }

    public LineWrapper getLineWrapper(String storeCode, String skuCode) {
        return LineWrappers.get(Pair.of(storeCode, skuCode));
    }

    public Collection<LineWrapper> getLineWrappers() {
        return this.LineWrappers.values();
    }


}
