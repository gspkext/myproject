package com.example.transaction.utils;

import com.example.transaction.entity.TradeBodyPool;
import com.example.transaction.entity.TradeObject;
import com.example.transaction.entity.block.BlockDownLoad;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BlockBaseUtils {

    public static TradeBodyPool genTbp(BlockDownLoad bdl) {
        TradeBodyPool tbp = new TradeBodyPool();
        Map<String, TradeObject> tbMap = new HashMap<>();
        
        String blockData = bdl.getBlock().data;
        JSONArray jsonArray = JSON.parseArray(blockData);
        
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TradeObject tradeObject = JSON.parseObject(jsonObject.toJSONString(), TradeObject.class);
            tbMap.put(tradeObject.getHash(), tradeObject);
        }
        
        tbp.setTbMap(tbMap);
        return tbp;
    }
} 