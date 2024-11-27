package com.example.transaction.entity.bo;

import com.example.transaction.entity.TProduct;

public class ProductBo extends TProduct {

    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
