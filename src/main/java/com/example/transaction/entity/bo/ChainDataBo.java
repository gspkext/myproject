package com.example.transaction.entity.bo;

import com.example.transaction.entity.TChainData;

public class ChainDataBo extends TChainData {

    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
