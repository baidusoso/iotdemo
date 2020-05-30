package com.tellhow.industry.iot.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.gateway.hikvision.BaseApi;
import com.tellhow.industry.iot.gateway.hikvision.BaseResponse;
import com.tellhow.industry.iot.gateway.hikvision.GatewayException;
import com.tellhow.industry.iot.gateway.hikvision.org.model.GetOrgListRequest;
import com.tellhow.industry.iot.gateway.hikvision.org.model.GetOrgListResponse;
import com.tellhow.industry.iot.gateway.hikvision.org.model.OrgInfo;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElasticsearchApi {

    public static final String ERR_FAIL_TO_ACCESS = "无法访问原平台";
    public static final String ERR_NO_DATA = "原平台返回空数据";
    public static final String ERR_RETURN_ERROR = "原平台返回错误";

    public static RestHighLevelClient getClient() {
        RestHighLevelClient client = null;

        try {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("10.69.212.11", 9200, "http")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static List<OrgInfo> getOrgList() {
        RestHighLevelClient client = getClient();
        if (client != null) {
            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("orgnization");
            searchRequest.types("orgnization");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(0);
            sourceBuilder.size(10000);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest);
                RestStatus status = searchResponse.status();
                if (status == RestStatus.OK) {
                    StringBuilder sb = new StringBuilder("[");
                    SearchHits hits = searchResponse.getHits();
                    SearchHit[] searchHits = hits.getHits();
                    for (SearchHit hit : searchHits) {
                        String sourceAsString = hit.getSourceAsString();
                        if (sb.length() > 1) {
                            sb.append(",");
                        }
                        sb.append(sourceAsString);
                    }
                    sb.append("]");
                    List<OrgInfo> result = parseOrgResult(sb.toString());
                    return result;
                } else {
                    throw new GatewayException(ERR_RETURN_ERROR + " statuscode:" + status.getStatus());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new GatewayException(ERR_FAIL_TO_ACCESS + ":" + e.getMessage());
            }
        }
        throw new GatewayException(ERR_FAIL_TO_ACCESS);
    }

    static List<OrgInfo> parseOrgResult(String data) {
        List<Org> response = JSON.parseObject(data, new TypeReference<List<Org>>() {
        });
        List<OrgInfo> result = new ArrayList<>();
        for (Org org : response) {
            result.add(org.toOrgInfo());
        }
        return result;
    }

    static class Org {
        public int id;
        public String name;
        public int parentId;

        OrgInfo toOrgInfo() {
            OrgInfo orgInfo = new OrgInfo();
            orgInfo.orgIndexCode = String.valueOf(id);
            orgInfo.orgName = name;
            orgInfo.parentOrgIndexCode = String.valueOf(parentId);
            return orgInfo;
        }
    }

    public static List<Account> getAccountList() {
        RestHighLevelClient client = getClient();
        if (client != null) {
            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("user");
            searchRequest.types("user");

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(0);
            sourceBuilder.size(10000);

            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = null;
            try {
                searchResponse = client.search(searchRequest);
                RestStatus status = searchResponse.status();
                if (status == RestStatus.OK) {
                    StringBuilder sb = new StringBuilder("[");
                    SearchHits hits = searchResponse.getHits();
                    SearchHit[] searchHits = hits.getHits();
                    for (SearchHit hit : searchHits) {
                        String sourceAsString = hit.getSourceAsString();
                        if (sb.length() > 1) {
                            sb.append(",");
                        }
                        sb.append(sourceAsString);
                    }
                    sb.append("]");
                    List<Account> result = parseAccountResult(sb.toString());
                    return result;
                } else {
                    throw new GatewayException(ERR_RETURN_ERROR + " statuscode:" + status.getStatus());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new GatewayException(ERR_FAIL_TO_ACCESS + ":" + e.getMessage());
            }
        }
        throw new GatewayException(ERR_FAIL_TO_ACCESS);
    }

    static List<Account> parseAccountResult(String data) {
        List<Account> response = JSON.parseObject(data, new TypeReference<List<Account>>() {
        });
        return response;
    }

    public static class Account {
        public String id;
        public String name;
        public int gender;
        public String no;
        public String certificateNum;
        public String mobile;
        public String orgId;
        public String username;
        public String usergroup;
        public String updateDate;
        public String faceId;
        public String facePic;
        public int delFlag;
    }
}
