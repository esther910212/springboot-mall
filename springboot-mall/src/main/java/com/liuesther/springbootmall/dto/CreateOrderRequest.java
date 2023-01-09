package com.liuesther.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {
    @NotEmpty //來加在 List 或是 Map 類型的變數上面 是去驗證說 這個集合 不可以是空的 就是裡面一定要有一個值存在才可以
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }

}
