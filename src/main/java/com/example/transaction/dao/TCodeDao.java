package com.example.transaction.dao;

import com.example.transaction.entity.TCode;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TCodeDao {
    void save(TCode code);

    List<TCode> queryByName(String productName);

    TCode queryByCode(String code);
}
