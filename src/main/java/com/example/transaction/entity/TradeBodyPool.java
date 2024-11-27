package com.example.transaction.entity;

import java.util.Map;

public class TradeBodyPool {
    private Map<String, TradeObject> tbMap;

    public Map<String, TradeObject> getTbMap() {
        return tbMap;
    }

    public void setTbMap(Map<String, TradeObject> tbMap) {
        this.tbMap = tbMap;
    }
} 