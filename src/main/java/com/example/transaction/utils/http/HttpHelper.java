package com.example.transaction.utils.http;

import com.example.transaction.entity.NoticeParams;
import com.example.transaction.entity.block.Block;
import com.example.transaction.entity.block.BlockDownLoad;
import com.example.transaction.service.impl.BlockServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class HttpHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    public static BlockDownLoad downLoadBlock(String ip, int port, NoticeParams np) {
        BlockDownLoad bdl = new BlockDownLoad();
        String url = "";
        if (StringUtils.isNotBlank(ip)) {
            url = "http://" + ip + ":" + port + "/mining/server/block.zip";
        } else {
            url = "http://" + np.getIp() + ":" + "8001" + "/mining/server/block.zip";
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        ZipInputStream zis = null;
        HttpPost httpPost = null;
        try {
            System.out.println("[+] Download block");
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Connection", "Close");
            httpPost.addHeader("Accept-Encoding", "GZIP");
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(np.toJSONString(), StandardCharsets.UTF_8);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            zis = new ZipInputStream(response.getEntity().getContent(), StandardCharsets.UTF_8);
            ZipEntry ze = null;
            Block block = null;
            String blockFileStr = "";
            String maxIndex = "";
            while ((ze = zis.getNextEntry()) != null) {
                if ("blockObject".equals(ze.getName())) {
                    block = BlockServiceImpl.getBlockObeject(zis);
                } else if ("maxBlockIndex".equals(ze.getName())) {
                    maxIndex = BlockServiceImpl.getMaxBlockIndexStr(zis);
                } else if ("blockFile".equals(ze.getName())) {
                    blockFileStr = BlockServiceImpl.getBlockFileStr(zis);
                    System.out.println(blockFileStr);
                }
            }
            if (block == null || StringUtils.isBlank(blockFileStr)) {
                System.out.println("[+] Block empty. No downloads.");
            }
            bdl.setBlock(block);
            bdl.setBlockFileStr(blockFileStr);
            bdl.setMaxBlockIndex(maxIndex);
            return bdl;
        } catch (Exception e) {
            System.out.println("[-] Connection to " + ip + " failed.");
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String checkBlock(String ip, Block block) {
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        try {
            String senMsg = new Gson().toJson(block);
            httpPost = new HttpPost("http://" + ip + ":8001/mining/checkBlock");
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(15000).setSocketTimeout(15000).build();
            httpPost.setConfig(requestConfig);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            StringEntity entity = new StringEntity(senMsg, StandardCharsets.UTF_8);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                String resultString = EntityUtils.toString(response.getEntity(), "utf-8");
                return resultString;
            } else {
                String str = "Request failed: " + response.getStatusLine().getStatusCode() + " ";
                return str;
            }
        } catch (ClientProtocolException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
