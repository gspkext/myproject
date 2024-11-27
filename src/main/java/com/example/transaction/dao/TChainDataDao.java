package com.example.transaction.dao;

import com.example.transaction.entity.TChainData;
import com.example.transaction.entity.TradeObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TChainDataDao {
    void save(TChainData tChainData);

    List<TChainData> queryByCode(String code);

    void updateData(TradeObject tradeObject);
}
