package com.example.transaction.dao;

import com.example.transaction.entity.TChainData;
import com.example.transaction.entity.TradeObject;
import com.example.transaction.entity.bo.ChainDataBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TChainDataDao {
    void addChainData(ChainDataBo chainDataBo);
    List<TChainData> getList();
    void updateData(TradeObject tradeObject);
} 