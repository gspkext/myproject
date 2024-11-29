package com.example.transaction.entity;

import java.util.List;

/**
 * 查询编码的视图对象
 */
public class QueryCodeVo {
    // 产品信息
    private TProduct product;
    // 链上数据列表
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
