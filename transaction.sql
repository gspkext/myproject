-- 创建数据库
USE transaction_project;

-- 区块信息表
CREATE TABLE IF NOT EXISTS t_block_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    block_index VARCHAR(64) NOT NULL COMMENT '区块索引',
    current_block_index VARCHAR(64) NOT NULL COMMENT '当前区块索引',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区块信息表';

-- 链数据表
CREATE TABLE IF NOT EXISTS t_chain_data (
    id VARCHAR(64) PRIMARY KEY COMMENT '主键ID',
    data TEXT NOT NULL COMMENT '交易数据',
    create_time VARCHAR(32) NOT NULL COMMENT '创建时间',
    hash VARCHAR(64) NOT NULL COMMENT '哈希值',
    block_index VARCHAR(64) DEFAULT NULL COMMENT '区块索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='链数据表';

-- 产品表
CREATE TABLE IF NOT EXISTS t_product (
    id VARCHAR(64) PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(255) NOT NULL COMMENT '产品名称',
    create_time VARCHAR(32) NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- 编码表
CREATE TABLE IF NOT EXISTS t_code (
    id VARCHAR(64) PRIMARY KEY COMMENT '主键ID',
    code VARCHAR(255) NOT NULL COMMENT '编码',
    product_id VARCHAR(64) NOT NULL COMMENT '产品ID',
    create_time VARCHAR(32) NOT NULL COMMENT '创建时间',
    FOREIGN KEY (product_id) REFERENCES t_product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='编码表';

-- 初始化区块信息
INSERT INTO t_block_info (block_index, current_block_index) VALUES ('0', '0'); 