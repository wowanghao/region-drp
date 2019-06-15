package com.jiajiayue.all.regiondrp.common.request;

import com.jiajiayue.all.regiondrp.biz.dto.wrapper.LineWrapper;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHao
 * @date 2019/5/26 23:11
 */
@Data
public class ListRequest<T> implements Serializable {

    private List<T> list = new ArrayList<>();

}
