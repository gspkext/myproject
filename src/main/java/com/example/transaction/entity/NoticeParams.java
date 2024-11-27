package com.example.transaction.entity;

public class NoticeParams {
    private String blockIndex;
    private String ip;
    private String hash;

    public NoticeParams(String blockIndex, String ip, String hash) {
        this.blockIndex = blockIndex;
        this.ip = ip;
        this.hash = hash;
    }

    public String getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(String blockIndex) {
        this.blockIndex = blockIndex;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
} 