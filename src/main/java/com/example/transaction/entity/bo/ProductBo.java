package com.example.transaction.entity.bo;

import com.example.transaction.entity.TProduct;

/**
 * 产品业务对象,扩展了TProduct实体
 */
public class ProductBo extends TProduct {

    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
