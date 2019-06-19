package com.jiajiayue.all.regiondrp.converter;

import com.jiajiayue.all.regiondrp.biz.dto.response.*;
import com.jiajiayue.all.regiondrp.biz.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author wh
 * @date 2019/05/26
 */
@Mapper(componentModel = "spring")
public interface ModelToResponseConverter {

    /**
     * request to model
     *
     * @param model MktStockInit
     * @return MktStockInit
     */

  /*  @Mappings({
            @Mapping(target = "lastmodify", expression = "java(com.jiajiayue.all.regiondrp.common.util.ConvertUtil.bytesToLong(model.getLastmodify()))")
    })
    NorpriceWaitResponse modelToResponse(NorpriceWait model);*/


    MktShopResponse modelToResponse(MktShop model);

/*    @Mappings({
            @Mapping(source = "request.storeCode", target = "storeCode"),
            @Mapping(source = "request.skuCode", target = "skuCode"),
            @Mapping(source = "request.inventoryInfo.sellableQuantity", target = "stockQuantity"),
            @Mapping(source = "request.inventoryInfo.realQuantity", target = "realQuantity"),
            @Mapping(source = "request.inventoryInfo.withholdQuantity", target = "withholdQuantity"),
            @Mapping(source = "request.inventoryInfo.occupyQuantity", target = "occupyQuantity"),
            @Mapping(source = "request.drpInventoryMeta.ratio", target = "stockScale"),
            @Mapping(target = "stockSourceType", expression = "java( (request.getDrpInventoryMeta() != null && request.getDrpInventoryMeta().getIsOnlineOnly() != null && request.getDrpInventoryMeta().getIsOnlineOnly()) ? 1 : 2 )")
    })
    StoreSkuStockDTO inventoryRecord2storeDto(O2OInventoryRecord request);*/

}
