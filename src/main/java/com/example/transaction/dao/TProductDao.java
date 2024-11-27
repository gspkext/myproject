package com.example.transaction.dao;

import com.example.transaction.entity.TProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TProductDao {
    void save(TProduct tProduct);

    List<TProduct> queryAll();

    TProduct queryById(int id);

    TProduct queryByName(String name);
}
