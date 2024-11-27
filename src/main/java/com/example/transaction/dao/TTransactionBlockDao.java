package com.example.transaction.dao;

import com.example.transaction.entity.block.BlockInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TTransactionBlockDao {
    BlockInfo queryBlockInfo();
    void updateBlock(BlockInfo blockInfo);
} 