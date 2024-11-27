package com.example.transaction.controller;

import com.example.transaction.dao.TCodeDao;
import com.example.transaction.dao.TProductDao;
import com.example.transaction.entity.JSONObject;
import com.example.transaction.entity.TCode;
import com.example.transaction.entity.TProduct;
import com.example.transaction.entity.bo.ProductBo;
import com.example.transaction.utils.DateUtils;
import com.example.transaction.utils.SnowflakeIdUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class CodeController {

    @Value("${node.ip}")
    private String ip;

    @Resource
    private TProductDao productDao;

    @Resource
    private TCodeDao codeDao;

    @RequestMapping("/createCode")
    public ResponseEntity<JSONObject> createCode(@RequestBody ProductBo productBo) {
        JSONObject jo = new JSONObject();

        if ("".equals(productBo.getPrivateKey()) || productBo.getPrivateKey() == null) {
            jo.setCode("-1");
            jo.setMsg("Private key can't be empty");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        if ("".equals(productBo.getId()) || productBo.getId() == 0) {
            jo.setCode("-1");
            jo.setMsg("id should be a non-zero value.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        TProduct tProduct = productDao.queryById(productBo.getId());
        Credentials credentials = Credentials.create(productBo.getPrivateKey());

        String address = credentials.getAddress();
        if (!address.equals(tProduct.getAddress())) {
            jo.setCode("-1");
            jo.setMsg("Address verification failed");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        SnowflakeIdUtils idWorker = new SnowflakeIdUtils(3, 1);

        String code = String.valueOf(idWorker.nextId());

        String imgUrl = "http://" + ip + ":8007/generateQRCode?code=" + code;


        TCode tCode = new TCode();
        tCode.setCode(code);
        tCode.setCreateTime(DateUtils.getTime());
        tCode.setProductName(tProduct.getProductName());
        tCode.setAddress(tProduct.getAddress());
        tCode.setImgUrl(imgUrl);
        codeDao.save(tCode);

        jo.setCode("1");
        jo.setMsg("Code has been added.");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }

    @RequestMapping("/queryAllCodeByProductId")
    public ResponseEntity<JSONObject> queryAllCode(@RequestBody ProductBo productBo) {
        JSONObject jo = new JSONObject();


        if ("".equals(productBo.getId()) || productBo.getId() == 0) {
            jo.setCode("-1");
            jo.setMsg("id should be a non-zero value.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        TProduct tProduct = productDao.queryById(productBo.getId());
        Credentials credentials = Credentials.create(productBo.getPrivateKey());
        // 获取 Ethereum 地址
        String address = credentials.getAddress();
        if (!address.equals(tProduct.getAddress())) {
            jo.setCode("-1");
            jo.setMsg("Address verification failed");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        List<TCode> list = codeDao.queryByName(tProduct.getProductName());

        if (list.size() == 0) {
            jo.setCode("-1");
            jo.setMsg("No data found");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }
        jo.setO(list);
        jo.setCode("1");
        jo.setMsg("Query successful");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }


    @RequestMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    @CrossOrigin
    public void generateQRCode(HttpServletResponse response, @RequestParam String code) throws WriterException, IOException {


        String url = "http://" + ip + ":8007/query?code=" + code;

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);

        int qrCodeSize = 200;
        BufferedImage image = new BufferedImage(qrCodeSize, qrCodeSize, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < qrCodeSize; x++) {
            for (int y = 0; y < qrCodeSize; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            response.getOutputStream().write(pngData);
        }
    }


}
