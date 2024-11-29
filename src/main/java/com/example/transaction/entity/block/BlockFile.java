package com.example.transaction.entity.block;

import com.example.transaction.entity.TradeObject;

import java.util.Map;

/**
 * 区块文件实体类
 */
public class BlockFile {
    // 交易对象映射表
    private Map<String, TradeObject> tbMap;
    // 区块索引
    private String blockIndex;
    // 区块哈希
    private String blockHash;
    // 前一个区块的哈希
    private String preBlockHash;

    public Map<String, TradeObject> getTbMap() {
        return tbMap;
    }

    public void setTbMap(Map<String, TradeObject> tbMap) {
        this.tbMap = tbMap;
    }

    public String getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(String blockIndex) {
        this.blockIndex = blockIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getPreBlockHash() {
        return preBlockHash;
    }

    public void setPreBlockHash(String preBlockHash) {
        this.preBlockHash = preBlockHash;
    }

    @Override
    public String toString() {
        return "BlockFile{" +
                "tbMap=" + tbMap +
                ", blockIndex='" + blockIndex + '\'' +
                ", blockHash='" + blockHash + '\'' +
                ", preBlockHash='" + preBlockHash + '\'' +
                '}';
    }
}
