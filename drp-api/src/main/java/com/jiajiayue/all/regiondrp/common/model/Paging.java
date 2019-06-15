package com.jiajiayue.all.regiondrp.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Paging<T> implements Serializable {
    private static final long serialVersionUID = 755183539178076901L;
    private Long total;
    private List<T> data;

    public Paging() {
    }

    public Paging(Long total, List<T> data) {
        this.data = data;
        this.total = total;
    }
}