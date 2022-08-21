package com.tensquare.common.entity;

import lombok.Data;

import java.util.List;
@Data
public class PageResult<T> {
    private Long total;
    private List<T> rows;
    public PageResult(Long total, List<T> rows) { super(); this.total = total; this.rows = rows; }
}
