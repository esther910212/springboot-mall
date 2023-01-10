package com.liuesther.springbootmall.util;

import java.util.List;

public class Page<T> {  //去處理分頁相關的 response java泛型 上面宣告後下面才能宣告

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> result;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset(Integer offset) {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }


}
