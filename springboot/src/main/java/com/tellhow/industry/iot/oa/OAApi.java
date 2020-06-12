package com.tellhow.industry.iot.oa;

import com.alibaba.fastjson.JSON;
import com.tellhow.industry.iot.account.model.Guest;
import com.tellhow.industry.iot.oa.model.OATokenReponse;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OAApi {
    final static String BASE_URL = "http://10.69.206.23";

    public static String getToken(String appId, String tenantId, String secret) {
        HttpGet httpGet = new HttpGet(BASE_URL + "/co/oapi/gettoken?appid=" + appId + "&tenantId=" + tenantId + "&secret=" + secret);
        String responseBody = execute(httpGet);
        if (responseBody != null) {
            OATokenReponse oaTokenReponse = JSON.parseObject(responseBody, OATokenReponse.class);
            if (oaTokenReponse != null && "0".equals(oaTokenReponse.access_token)) {
                String accessToken = oaTokenReponse.access_token;
                if (!StringUtils.isEmpty(accessToken)) {
                    return accessToken;
                }
            }
        }
        throw new OAException("获取Token失败");
    }

    public static void sendNewGuestMessage(String token, Guest guest) {

    }

    static String execute(HttpRequestBase requestBase) throws OAException {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求

        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(requestBase);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            String responseBody = null;
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                responseEntity.writeTo(byteArrayOutputStream);
                responseBody = new String(byteArrayOutputStream.toByteArray());
            }
            return responseBody;
        } catch (ParseException | IOException e) {
            throw new OAException(e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
