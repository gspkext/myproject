package com.example.transaction.controller;

import com.example.transaction.dao.TChainDataDao;
import com.example.transaction.dao.TCodeDao;
import com.example.transaction.dao.TProductDao;
import com.example.transaction.entity.*;
import com.example.transaction.entity.bo.ChainDataBo;
import com.example.transaction.utils.DateUtils;
import com.example.transaction.utils.EthUtils;
import com.example.transaction.utils.PendingUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Sign;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@RestController
public class ChainController {

    @Value("${node.ip}")
    private String ip;

    @Resource
    private TCodeDao codeDao;

    @Resource
    private TChainDataDao chainDataDao;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TProductDao productDao;

    @RequestMapping("/toChain")
    public ResponseEntity<JSONObject> toChain(@RequestBody ChainDataBo chainDataBo) throws Exception {
        JSONObject jo = new JSONObject();

        if ("".equals(chainDataBo.getCode()) || chainDataBo.getCode() == null) {
            jo.setCode("-1");
            jo.setMsg("Code cannot be empty.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        if ("".equals(chainDataBo.getPrivateKey()) || chainDataBo.getPrivateKey() == null) {
            jo.setCode("-1");
            jo.setMsg("PrivateKey cannot be empty.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        if ("".equals(chainDataBo.getContent()) || chainDataBo.getContent() == null) {
            jo.setCode("-1");
            jo.setMsg("Content cannot be empty.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        if ("".equals(chainDataBo.getProcessName()) || chainDataBo.getProcessName() == null) {
            jo.setCode("-1");
            jo.setMsg("ProcessName cannot be empty.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        TCode tCode = codeDao.queryByCode(chainDataBo.getCode());


        TChainData tChainData = new TChainData();
        tChainData.setFrom(tCode.getAddress());
        tChainData.setTo("system");
        tChainData.setContent(chainDataBo.getContent());
        tChainData.setCreateTime(DateUtils.getTime());
        tChainData.setCode(tCode.getCode());
        tChainData.setProductName(tCode.getProductName());
        tChainData.setProcessName(chainDataBo.getProcessName());
        tChainData.setChainStatus("0");
        tChainData.setBlockIndex("");

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
        String jsonStr = gson.toJson(tChainData);


        BigInteger pri = new BigInteger(chainDataBo.getPrivateKey(), 16);

        TradeObject tradeObject = new TradeObject();
        tradeObject.setFrom(tCode.getAddress());
        tradeObject.setTo("system");
        tradeObject.setType("1");
        tradeObject.setContent(jsonStr);
        tradeObject.setJsoncreatetime(DateUtils.getTime());
        tradeObject.setObjToString(tradeObject.toString());

        Sign.SignatureData signatureData = EthUtils.signMessage(tradeObject.toString(), pri);
        String sign = EthUtils.getSignStr(signatureData);
        tradeObject.setSign(sign);

        String hashNo = PendingUtils.genTradeNo(tradeObject);
        tChainData.setHashNo(hashNo);
        chainDataDao.save(tChainData);

        String url = "http://" + ip + ":8001/data/trade";
        restTemplate.postForEntity(url, tradeObject, TradeObject.class);

        jo.setCode("1");
        jo.setMsg("Trade success");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }


    @RequestMapping("/queryCode")
    public ResponseEntity<JSONObject> queryCode(String code) {
        JSONObject jo = new JSONObject();

        if ("".equals(code) || code == null) {
            jo.setCode("-1");
            jo.setMsg("Code cannot be empty.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }


        List<TChainData> tChainData = chainDataDao.queryByCode(code);
        if (tChainData.size() == 0) {
            jo.setCode("-1");
            jo.setMsg("No data found.");
            return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        }

        String productName = tChainData.get(0).getProductName();
        TProduct tProduct = productDao.queryByName(productName);
        QueryCodeVo queryCodeVo = new QueryCodeVo();
        queryCodeVo.setList(tChainData);
        queryCodeVo.setProduct(tProduct);

        jo.setO(queryCodeVo);
        jo.setCode("1");
        jo.setMsg("Query success");
        return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
    }

}
