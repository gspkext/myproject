package com.example.transaction.dao;

import com.example.transaction.entity.block.BlockInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface TBlockDao {
    BlockInfo queryBlockInfo();

    void updateBlock(BlockInfo blockInfo);
}
