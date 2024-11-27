package com.example.transaction.controller;

import com.example.transaction.dao.TTransactionBlockDao;
import com.example.transaction.dao.TChainDataDao;
import com.example.transaction.entity.TChainData;
import com.example.transaction.entity.bo.ChainDataBo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/chain")
public class ChainController {
    @Resource
    private TTransactionBlockDao tTransactionBlockDao;
    
    @Resource
    private TChainDataDao tChainDataDao;

    @PostMapping("/add")
    public String addChainData(@RequestBody ChainDataBo chainDataBo) {
        tChainDataDao.addChainData(chainDataBo);
        return "success";
    }

    @GetMapping("/list")
    public List<TChainData> getList() {
        return tChainDataDao.getList();
    }
} 