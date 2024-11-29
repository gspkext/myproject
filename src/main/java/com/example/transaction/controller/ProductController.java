package com.example.transaction.controller;

import com.example.transaction.dao.TProductDao;
import com.example.transaction.entity.JSONObject;
import com.example.transaction.entity.TProduct;
import com.example.transaction.entity.bo.ProductBo;
import com.example.transaction.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

    @Resource
    private TProductDao tProductDao;

    /**
     * 添加产品接口
     * @param productBo 产品信息对象
     * @return 响应结果
     */
    @RequestMapping("/product/add")
    public ResponseEntity<JSONObject> add(@RequestBody ProductBo productBo) {
        JSONObject jo = new JSONObject();

        // 验证产品名称
        if ("".equals(productBo.getProductName()) || productBo.getProductName() == null) {
            jo.setCode("-1");
            jo.setMsg("Product name cannot be empty");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }
        if ("".equals(productBo.getProductDesc()) || productBo.getProductDesc() == null) {
            jo.setCode("-1");
            jo.setMsg("Product description cannot be empty");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }
        if ("".equals(productBo.getAddress()) || productBo.getAddress() == null) {
            jo.setCode("-1");
            jo.setMsg("Address cannot be empty");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }
        if ("".equals(productBo.getPrivateKey()) || productBo.getPrivateKey() == null) {
            jo.setCode("-1");
            jo.setMsg("Private key cannot be empty");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        // 检查产品名是否已存在
        TProduct exist = tProductDao.queryByName(productBo.getProductName());
        if (exist != null) {
            jo.setCode("-1");
            jo.setMsg("Product name occupied. Choose a new one.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        // 验证私钥和地址是否匹配
        Credentials credentials = Credentials.create(productBo.getPrivateKey());
        String address = credentials.getAddress();
        if (!address.equals(productBo.getAddress())) {
            jo.setCode("-1");
            jo.setMsg("Address verification failed. Please try again.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        // 创建并保存产品信息
        TProduct tProduct = new TProduct();
        tProduct.setProductName(productBo.getProductName());
        tProduct.setProductDesc(productBo.getProductDesc());
        tProduct.setAddress(productBo.getAddress());
        tProduct.setCreateTime(DateUtils.getTime());
        tProductDao.save(tProduct);

        // 返回成功响应
        jo.setO("");
        jo.setMsg("Add product success");
        jo.setCode("1");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }

    /**
     * 查询所有产品接口
     * @return 响应结果
     */
    @RequestMapping("/queryProducts")
    public ResponseEntity<JSONObject> queryProducts() {
        JSONObject jo = new JSONObject();

        // 查询所有产品
        List<TProduct> product = tProductDao.queryAll();
        if (product.size() == 0) {
            jo.setO("");
            jo.setMsg("No data found");
            jo.setCode("-1");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        // 返回查询结果
        jo.setO(product);
        jo.setMsg("Query success");
        jo.setCode("1");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }

}
