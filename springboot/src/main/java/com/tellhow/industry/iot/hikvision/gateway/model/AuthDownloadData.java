package com.tellhow.industry.iot.hikvision.gateway.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthDownloadData {
    public String taskId;
    public List<ResourceInfo> resourceInfos = new ArrayList<>();
    public List<PersonInfo> personInfos = new ArrayList<>();

    public AuthDownloadData(String taskId, ElasticsearchApi.GatewayPolicy gatewayPolicy, ElasticsearchApi.Account account, Gateway.Door doorGateway) {
        this.taskId = taskId;
        ResourceInfo resourceInfo = new ResourceInfo(doorGateway);
        resourceInfos.add(resourceInfo);
        PersonInfo personInfo = new PersonInfo(gatewayPolicy, account);
        personInfos.add(personInfo);
    }

    public static class ResourceInfo {
        public String resourceIndexCode;
        public String resourceType;
        public List<Integer> channelNos = new ArrayList<>();

        public ResourceInfo(String resourceIndexCode, String resourceType, int channelNo) {
            this.resourceIndexCode = resourceIndexCode;
            this.resourceType = resourceType;
            this.channelNos.add(channelNo);
        }

        public ResourceInfo(Gateway.Door doorGateway) {
            this.resourceIndexCode = doorGateway.doorIndexCode;
            this.resourceType = doorGateway.channelType;
            this.channelNos.add(Integer.parseInt(doorGateway.channelNo));
        }
    }

    public static class PersonInfo {

        public String personId;
        public int operatorType;
        public String startTime;
        public String endTime;
        public int personType = 1;
        public String name;

        public List<Card> cards;
        public Face face;

        public PersonInfo(ElasticsearchApi.GatewayPolicy gatewayPolicy, ElasticsearchApi.Account account) {
            this.personId = account.id;
            this.startTime = gatewayPolicy.startAt;
            this.operatorType = 0;
            this.endTime = gatewayPolicy.endAt;
            if (account.isGuest()) {
                this.personType = 2;
                this.name = account.name;
                cards = new ArrayList<>();
                String cardNo = account.mobile;
                if (StringUtils.isEmpty(cardNo)) {
                    cardNo = account.certificateNum;
                }
                Card card = new Card(cardNo);
                cards.add(card);

                face = new Face(cardNo, account.faceId, account.getFacePicUrl());
            }
        }
    }

    public static class Card {
        public String card;
        public int status;
        public int cardType;

        public Card(String card) {
            this.card = card;
            this.status = 0;
            this.cardType = 1;
        }

        public Card(int status, int cardType) {
            this.status = status;
            this.cardType = cardType;
        }
    }

    public static class Face {
        public String card;
        public Map<String, String> data = new HashMap<>();
        @JSONField(serialize = false, deserialize = false)
        public transient String faceId;

        @JSONField(serialize = false, deserialize = false)
        public transient String faceUrl;

        public Face(String card, String faceId, String faceUrl) {
            this.card = card;
            this.faceId = faceId;
            this.faceUrl = faceUrl;
            data.put(faceId, faceUrl);
        }

    }


}
