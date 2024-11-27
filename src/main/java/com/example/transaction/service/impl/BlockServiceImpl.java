package com.example.transaction.service.impl;

import com.example.transaction.dao.TTransactionBlockDao;
import com.example.transaction.entity.block.BlockInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlockServiceImpl {

    @Resource
    private TTransactionBlockDao tTransactionBlockDao;

    public BlockInfo queryBlockInfo() {
        return tTransactionBlockDao.queryBlockInfo();
    }

    public void updateBlock(BlockInfo blockInfo) {
        tTransactionBlockDao.updateBlock(blockInfo);
    }
} 