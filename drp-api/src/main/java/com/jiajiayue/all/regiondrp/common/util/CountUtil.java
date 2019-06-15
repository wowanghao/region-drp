package com.jiajiayue.all.regiondrp.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHao
 * @date 2019/5/20 15:13
 */
public class CountUtil {

    /**
     * 通过总数量计算出总页数，返回页数list
     *
     * @param totalCount
     * @return
     */
    public static List getPageListByTotalCount(Long totalCount, int pageNumber) {
        int page = (int) Math.ceil((double) totalCount / pageNumber);
        List<Integer> pageList = new ArrayList<>();
        for (int i = 1; i <= page; i++) {
            pageList.add(i);
        }
        return pageList;
    }
}
