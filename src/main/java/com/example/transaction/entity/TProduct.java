package com.example.transaction.entity;

/**
 * 产品实体类
 */
public class TProduct {
    // 主键ID
    private int id;
    // 产品名称
    private String productName;
    // 产品描述
    private String productDesc;
    // 创建时间
    private String createTime;
    // 区块链地址
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
