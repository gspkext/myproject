package com.example.transaction.component;

import com.example.transaction.dao.TTransactionBlockDao;
import com.example.transaction.dao.TChainDataDao;
import com.example.transaction.entity.NoticeParams;
import com.example.transaction.entity.TradeBodyPool;
import com.example.transaction.entity.TradeObject;
import com.example.transaction.entity.block.BlockDownLoad;
import com.example.transaction.entity.block.BlockInfo;
import com.example.transaction.utils.BlockBaseUtils;
import com.example.transaction.utils.http.HttpHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UpdateTransactionTask {

    @Value("${node.ip}")
    private String ip;

    @Resource
    private TTransactionBlockDao tTransactionBlockDao;

    @Resource
    private TChainDataDao tChainDataDao;

    @Scheduled(cron = "*/5 * * * * ?")
    public void updateTransaction() throws Exception {
        BlockInfo blockInfo = tTransactionBlockDao.queryBlockInfo();
        String nextIndex = String.valueOf(Integer.valueOf(blockInfo.getCurrentBlockIndex()) + 1);
        System.out.println("执行定时任务下载交易区块--------下载区块为：" + nextIndex);
        updateTransactionBlock(nextIndex);
    }

    public void updateTransactionBlock(String blockIndex) throws Exception {
        BlockDownLoad bdl = null;
        NoticeParams np = new NoticeParams(blockIndex.toString(), ip,"");
        bdl = HttpHelper.downLoadBlock(ip, 8001, np);

        if (bdl == null ||bdl.getBlock() ==null ||Integer.valueOf(bdl.getBlock().blockIndex) < Integer.valueOf(blockIndex)){
            return;
        }

        TradeBodyPool tbp = BlockBaseUtils.genTbp(bdl);
        List<TradeObject> list = new ArrayList<>();

        Map<String, TradeObject> tbMap = tbp.getTbMap();
        for (Map.Entry<String, TradeObject> entry : tbMap.entrySet()) {
            TradeObject tradeObject = entry.getValue();
            list.add(tradeObject);
        }

        for (TradeObject tradeObject : list) {
            tradeObject.setBlockIndex(blockIndex);
            tChainDataDao.updateData(tradeObject);
        }

        BlockInfo blockInfo = new BlockInfo();
        blockInfo.setBlockIndex(bdl.getMaxBlockIndex());
        blockInfo.setCurrentBlockIndex(String.valueOf(Integer.valueOf(blockIndex)));
        tTransactionBlockDao.updateBlock(blockInfo);
        System.out.println("交易更新完成-------");
    }
}