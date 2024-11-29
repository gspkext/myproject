package com.example.transaction.dao;

import com.example.transaction.entity.TChainData;
import com.example.transaction.entity.TradeObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 区块链数据访问接口
 */
@Repository
public interface TChainDataDao {
    // 保存链上数据
    void save(TChainData tChainData);

    // 根据编码查询链上数据
    List<TChainData> queryByCode(String code);

    // 更新交易数据
    void updateData(TradeObject tradeObject);
}
