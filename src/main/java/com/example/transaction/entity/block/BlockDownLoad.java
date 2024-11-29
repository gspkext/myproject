package com.example.transaction.entity.block;

/**
 * 区块下载实体类
 */
public class BlockDownLoad {
    // 区块信息
    private Block block;
    // 区块文件内容字符串
    private String blockFileStr;
    // 最大区块索引
    private String maxBlockIndex;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getBlockFileStr() {
        return blockFileStr;
    }

    public void setBlockFileStr(String blockFileStr) {
        this.blockFileStr = blockFileStr;
    }

    public String getMaxBlockIndex() {
        return maxBlockIndex;
    }

    public void setMaxBlockIndex(String maxBlockIndex) {
        this.maxBlockIndex = maxBlockIndex;
    }
}
