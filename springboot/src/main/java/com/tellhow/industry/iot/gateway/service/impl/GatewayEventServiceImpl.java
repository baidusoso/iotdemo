package com.tellhow.industry.iot.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tellhow.industry.iot.account.dao.AccountDao;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.gateway.dao.GatewayEventDao;
import com.tellhow.industry.iot.gateway.model.AuditLog;
import com.tellhow.industry.iot.gateway.model.DoorEvent;
import com.tellhow.industry.iot.gateway.service.GatewayEventService;
import com.tellhow.industry.iot.util.CommonUtil;
import com.tellhow.industry.iot.util.HttpClientFactory;
import com.tellhow.industry.iot.util.HttpConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GatewayEventServiceImpl implements GatewayEventService {

    static final Set<String> AUDIT_DOOR_NAME_SET = new HashSet<String>() {
        {
            add("东二门中人脸识别出_门1");
            add("东二门人脸识别中间进_门1");
            add("东二门人脸识别北出_门1");
            add("东二门人脸识别南出_门1");
            add("东二门人脸识别南进_门1");
            add("东二门北人脸识别进_门1");
            add("人脸识别进_门1");
            add("人脸识别出_门1");
        }
    };

    @Autowired
    GatewayEventDao gatewayEventDao;

    @Autowired
    AccountDao accountDao;

    @Override
    public JSONObject saveEvent(DoorEvent doorEvent) {
        AuditLog auditLog = new AuditLog();
        auditLog.setSrcName(doorEvent.params.events.get(0).srcName);
        auditLog.setExtEventInOut(doorEvent.params.events.get(0).data.ExtEventInOut);
        auditLog.setHappenTime(doorEvent.params.events.get(0).happenTime);
        int eventType = doorEvent.params.events.get(0).eventType.intValue();
        if (eventType == 196893 || eventType == 197162) {
            auditLog.setResult("允许通过");
        }
        if (doorEvent.params.events.get(0).data.ExtEventCardNo > 0) {
            ElasticsearchApi.Account account = accountDao.getAccountByCertificateNum(auditLog.getMobiles());
            if (account != null) {
                auditLog.setUserID(account.id);
                auditLog.setName(account.name);
                auditLog.setNo(account.no);
                auditLog.setMobiles(account.mobile);
                auditLog.setCertificateNum(account.certificateNum);
                auditLog.setUserGroup(account.usergroup);
                auditLog.setGender(account.gender);

                gatewayEventDao.saveAuditLog(auditLog);
                if (!account.isGuest() && AUDIT_DOOR_NAME_SET.contains(auditLog.getSrcName())) {
                    commitAuditLog2IAM(auditLog);
                }
            }
        }
        return null;
    }

    @Override
    public JSONObject getEventList(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = gatewayEventDao.getEventCount(jsonObject);
        List<JSONObject> eventList = gatewayEventDao.getEventList(jsonObject);
        return CommonUtil.successPage(jsonObject, eventList, count);
    }

    static void commitAuditLog2IAM(AuditLog auditLog) {
        String url = "http://10.69.206.48/snap-app-application/oapi/attendance/user/access/sync";
        //iamRequest.userId = auditLog.getUserID();
        //System.out.println(auditLog.getSrcName());
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat sdfISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        try {
            Date data = sdfISO8601.parse(auditLog.getHappenTime());
            Long tim = data.getTime();
            System.out.println(tim);
            map.put("tenantId", "lfdc");
            map.put("userId", auditLog.getUserID());
            map.put("time", tim.toString());
            map.put("inOut", auditLog.getExtEventInOut().toString());
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HttpConstant.UTF8_ENCODE);
            httpPost.setEntity(entity);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(httpEntity);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
