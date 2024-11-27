package com.example.transaction.entity;

import java.util.List;

public class QueryCodeVo {

    private TProduct product;
    private List<TChainData> list;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public List<TChainData> getList() {
        return list;
    }

    public void setList(List<TChainData> list) {
        this.list = list;
    }
}
