package com.tellhow.industry.iot.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.tellhow.industry.iot.account.model.Guest;
import com.tellhow.industry.iot.account.model.IAMAccount;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.gateway.model.Gateway;
import com.tellhow.industry.iot.hikvision.org.model.OrgInfo;
import com.tellhow.industry.iot.system.model.Org;
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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

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
            SearchRequest searchRequest = new SearchRequest("organization");
            searchRequest.types("organization");

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

    public static class Account implements Serializable {
        public String id;
        public String name;
        public String gender;
        public String no;
        public String certificateNum;
        public String mobile;
        public String email;
        public String orgId;
        public String username;
        public String usergroup;
        public String updateDate;
        public String faceId;
        public String facePic;
        public String loginName;
        public int delFlag;

        public Account() {

        }

        public Account(IAMAccount account) {
            this.id = account.id;
            this.name = account.name;
            this.gender = account.gender;
            this.no = account.no;
            this.certificateNum = account.idCard;
            this.mobile = account.mobile;
            this.email = account.email;
            this.username = account.loginName;
            this.usergroup = "厂内人员";
            this.loginName = account.loginName;
            if (account.delFlag != null) {
                this.delFlag = Integer.parseInt(account.delFlag);
            }
        }

        public Account(Guest guest) {
            this.id = guest.userId;
            this.name = guest.name;
            this.gender = guest.gender;
            this.certificateNum = guest.certificateNum;
            this.mobile = guest.mobile;
            this.usergroup = "外来访客";
        }

        public boolean isGuest() {
            return "外来访客".equals(usergroup);
        }

        public String getFacePicUrl() {
            if (isGuest()) {
                return "http://10.69.212.11:3000/face/visitor/" + facePic;
            }
            return "http://10.69.212.11:3000/face/user/" + facePic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getCertificateNum() {
            return certificateNum;
        }

        public void setCertificateNum(String certificateNum) {
            this.certificateNum = certificateNum;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public void setUsergroup(String usergroup) {
            this.usergroup = usergroup;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }

        public String getFacePic() {
            return facePic;
        }

        public void setFacePic(String facePic) {
            this.facePic = facePic;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }
    }

    public static List<GatewayPolicy> getGatewayPolicyList() {
        RestHighLevelClient client = getClient();
        if (client != null) {
            // 1、创建search请求
            //SearchRequest searchRequest = new SearchRequest();
            SearchRequest searchRequest = new SearchRequest("gateway_policy");
            searchRequest.types("gateway_policy");

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
                    List<GatewayPolicy> result = parseGatewayPolicyResult(sb.toString());
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

    static List<GatewayPolicy> parseGatewayPolicyResult(String data) {
        List<GatewayPolicy> response = JSON.parseObject(data, new TypeReference<List<GatewayPolicy>>() {
        });
        return response;
    }

    public static class GatewayPolicy {
        public String id;
        public String gatewayId;
        public String userId;
        public String startAt;
        public String endAt;

        public int personStatus;
        public int cardStatus;
        public int faceStatus;
        public String configTime;

//        @JSONField(serialize = false, deserialize = false)
//        public transient Account account;
//
//        @JSONField(serialize = false, deserialize = false)
//        public transient Gateway.Door doorGateway;
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = sdf.parse("2037-12-31T08:00:00.000+08:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf2.format(date);
        System.out.println(s);

        Map<String, String> map = new HashMap<>();
        map.put("personId", "1");
        System.out.println(JSON.toJSON(map));
    }
}
