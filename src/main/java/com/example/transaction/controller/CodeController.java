package com.example.transaction.controller;

import com.example.transaction.entity.QueryCodeVo;
import com.example.transaction.entity.TCode;
import com.example.transaction.dao.TCodeDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Resource
    private TCodeDao tCodeDao;

    @PostMapping("/add")
    public String addCode(@RequestBody TCode tCode) {
        tCodeDao.addCode(tCode);
        return "success";
    }

    @PostMapping("/query")
    public List<TCode> queryCode(@RequestBody QueryCodeVo queryCodeVo) {
        return tCodeDao.queryCode(queryCodeVo);
    }
} 