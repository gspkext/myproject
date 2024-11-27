package com.example.transaction.dao;

import com.example.transaction.entity.TProduct;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TProductDao {
    List<TProduct> selectAll();
    int insert(TProduct product);
    // 其他可能的方法...
} 