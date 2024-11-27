package com.example.transaction.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class TradeBodyPool implements Serializable {

    Map<String, TradeObject> tbMap;

    public TradeBodyPool() {
        super();
        tbMap = new HashMap<String, TradeObject>();

    }

    public Map<String, TradeObject> getTbMap() {
        return tbMap;
    }

    public void setTbMap(Map<String, TradeObject> tbMap) {
        this.tbMap = tbMap;
    }
}
