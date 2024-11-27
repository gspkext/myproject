package com.example.transaction.entity.block;

public class BlockDownLoad {
    private Block block;
    private String maxBlockIndex;
    private BlockFile blockFile;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public String getMaxBlockIndex() {
        return maxBlockIndex;
    }

    public void setMaxBlockIndex(String maxBlockIndex) {
        this.maxBlockIndex = maxBlockIndex;
    }

    public BlockFile getBlockFile() {
        return blockFile;
    }

    public void setBlockFile(BlockFile blockFile) {
        this.blockFile = blockFile;
    }
} 