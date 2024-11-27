package com.example.transaction.dao;

import com.example.transaction.entity.QueryCodeVo;
import com.example.transaction.entity.TCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TCodeDao {
    void addCode(TCode tCode);
    List<TCode> queryCode(QueryCodeVo queryCodeVo);
} 