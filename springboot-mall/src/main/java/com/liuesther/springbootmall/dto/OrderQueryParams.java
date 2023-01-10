package com.liuesther.springbootmall.dto;

public class OrderQueryParams { //前端傳過來的一些請求參數 就是把他收集在這個 class 裡面

    private Integer userId;
    private Integer limit;
    private Integer offset;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
