package com.example.transaction.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 交易体池,用于存储待处理的交易
 */
public class TradeBodyPool implements Serializable {
    // 交易对象映射表
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
