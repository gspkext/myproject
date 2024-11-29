package com.example.transaction.utils;

import com.example.transaction.entity.TradeBodyPool;
import com.example.transaction.entity.TradeObject;
import com.example.transaction.entity.block.BlockDownLoad;
import com.example.transaction.entity.block.BlockFile;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区块基础工具类
 */
public class BlockBaseUtils {

    /**
     * 从区块下载对象生成交易体池
     * @param bdl 区块下载对象
     * @return 交易体池对象
     */
    public static TradeBodyPool genTbp(BlockDownLoad bdl) throws Exception {
        TradeBodyPool tbp = new TradeBodyPool();
        String blockFileStr = bdl.getBlockFileStr();
        tbp.setTbMap(genTbm(blockFileStr));
        return tbp;
    }

    /**
     * 区块字符串转换为交易对象映射
     * @param blockFileStr 区块文件字符串
     * @return 交易对象映射
     */
    public static Map<String, TradeObject> genTbm(String blockFileStr) throws Exception {
        Map<String, TradeObject> tbMap = new HashMap<String, TradeObject>();
        try {
            // 使用Gson解析区块文件字符串
            BlockFile blockFile = new Gson().fromJson(blockFileStr, BlockFile.class);
            tbMap = blockFile.getTbMap();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return tbMap;
    }

    /**
     * Map对象转换为交易体对象
     * @param map 源Map对象
     * @return 交易体对象
     */
    public static TradeObject mapToTradeBody(Map map) {
        TradeObject tb = null;
        try {
            tb = new TradeObject();
            // 通过反射设置字段值
            Field[] fields = TradeObject.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(tb, map.get(field.getName()));
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return tb;
    }

    /**
     * 将字符串列表转换为字节数组
     * @param list 字符串列表
     * @return 字节数组
     */
    public static byte[] listToByteArray(List<String> list) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(list);
        oos.flush();
        return bos.toByteArray();
    }

    /**
     * Map对象转换为指定类型的对象
     * @param map 源Map对象
     * @param clasz 目标类型
     * @return 转换后的对象
     */
    public static <T> T mapToTrade(Map map, Class<T> clasz) {
        T tb = null;
        try {
            try {
                tb = clasz.newInstance();
            } catch (InstantiationException e) {
                e.getMessage();
                return null;
            }
            // 通过反射设置字段值
            Field[] fields = clasz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(tb, map.get(field.getName()));
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return tb;
    }
}
