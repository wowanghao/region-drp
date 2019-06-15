package com.jiajiayue.all.regiondrp.biz.dto.request;

import com.jiajiayue.all.regiondrp.biz.dto.DrpInventoryLine;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wh
 */
@Data
public class DrpInventorySyncRequest implements Serializable {

    private List<DrpInventoryLine> data = new ArrayList<>();

}
