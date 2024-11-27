package com.example.transaction.utils.http;

import com.example.transaction.entity.NoticeParams;
import com.example.transaction.entity.block.BlockDownLoad;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HttpHelper {

    public static BlockDownLoad downLoadBlock(String ip, int port, NoticeParams params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(params), headers);
            String url = "http://" + ip + ":" + port + "/block/download";
            
            String response = restTemplate.postForObject(url, request, String.class);
            return JSON.parseObject(response, BlockDownLoad.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 