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
//                    throw new GatewayException(ERR_RETURN_ERROR + " statuscode:" + status.getStatus());
                }
            } catch (IOException e) {
                e.printStackTrace();
//                throw new GatewayException(ERR_FAIL_TO_ACCESS + ":" + e.getMessage());
            }
        }
//        throw new GatewayException(ERR_FAIL_TO_ACCESS);
        return parseAccountResult(mock);
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

    public static String mock="[{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-11-06 09:56:51\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12024582@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"30d221c1e7bb72cac883a4ab3dc39a05\",\"idCard\":\"220284198511017011\",\"loginFlag\":\"1\",\"loginName\":\"haoyuwen\",\"mobile\":\"15124473470\",\"name\":\"郝毓文\",\"nation\":\"\",\"no\":\"12024582\",\"orgId\":\"2060\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-06 09:56:51\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"haoyuwen\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"220284198511017011\",\"faceId\":\"b2f42465-4d4c-4a8a-9fe4-0efb8d3c91ca\",\"facePic\":\"30d221c1e7bb72cac883a4ab3dc39a05.jpg\",\"timestamp\":1573005411000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 15:55:26\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12089778@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"43e7229489cc46aa8232dbf1ff6ed8b2\",\"idCard\":\"131102198804174415\",\"loginDate\":\"2019-06-14 09:23:16\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.202.74\",\"loginName\":\"zhangxinran\",\"mobile\":\"15103167788\",\"name\":\"张心然\",\"nation\":\"\",\"no\":\"12089778\",\"orgId\":\"2060\",\"partyCost\":-1,\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"sort\":-1,\"token\":\"96df38c1b696a558aae7fad9dda0f904eb8fdcffd3cc01644b936620\",\"updateBy\":\"1\",\"updateDate\":\"2019-10-16 19:43:11\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"zhangxinran\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"131102198804174415\",\"faceId\":\"b183688c-d8b2-49bb-bbc9-f24d5b794ae2\",\"facePic\":\"43e7229489cc46aa8232dbf1ff6ed8b2.jpg\",\"timestamp\":1571226191000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 15:41:13\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12092306@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"51bf32e3506b456b987fd3ce1cc88b70\",\"idCard\":\"130183199301180030\",\"loginDate\":\"2019-09-03 21:56:29\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.197.55\",\"loginName\":\"zhangluhua\",\"mobile\":\"18503161568\",\"name\":\"张禄华\",\"nation\":\"\",\"no\":\"12092306\",\"orgId\":\"2031\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"e5eced29219cd1b666d4022cabe0143f1c0cf8ea0bb83bb4bc1c0c27\",\"updateBy\":\"1\",\"updateDate\":\"2019-07-15 10:23:18\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"zhangluhua\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"130183199301180030\",\"faceId\":\"a9ca35e3-ef94-4560-8061-97d9d6c3e632\",\"facePic\":\"51bf32e3506b456b987fd3ce1cc88b70.jpg\",\"timestamp\":1563157398000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 15:26:42\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12047879@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"52ab19eb400349948c801110210c066d\",\"idCard\":\"140104197606040036\",\"loginDate\":\"2019-08-20 10:55:45\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.202.103\",\"loginName\":\"like\",\"mobile\":\"18503168338\",\"name\":\"李科\",\"nation\":\"\",\"no\":\"12047879\",\"orgId\":\"2025\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"c2038820a16b920c1eb60c4c1160a79794d6642875cbb6e89aa9df89\",\"updateBy\":\"1\",\"updateDate\":\"2019-05-27 17:29:39\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"like\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"140104197606040036\",\"faceId\":\"c0a83a82-e70b-4668-8d47-0960528357d9\",\"facePic\":\"52ab19eb400349948c801110210c066d.jpg\",\"timestamp\":1558949379000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 17:18:09\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000093@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"4aa7bdedd2534e65bb2a5669eaea9c75\",\"idCard\":\"132527197205188538\",\"loginDate\":\"2019-09-03 11:12:06\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.207.20\",\"loginName\":\"zhaoyou\",\"mobile\":\"12000000093\",\"name\":\"赵有\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2044\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"48426d28c3f461466509aeec920c290428fbafc5f6d321dd71326299\",\"updateBy\":\"1\",\"updateDate\":\"2019-07-09 08:44:14\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"zhaoyou\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"132527197205188538\",\"faceId\":\"0c600db9-6875-4e1d-95d5-266e1cc1558e\",\"facePic\":\"4aa7bdedd2534e65bb2a5669eaea9c75.jpg\",\"timestamp\":1562633054000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-07-29 09:37:26\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000082@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"41154b528e3345d4b95c3288e98d2a2a\",\"idCard\":\"140225200005081811\",\"loginDate\":\"2019-09-03 10:42:07\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.197.55\",\"loginName\":\"zhenb\",\"mobile\":\"12000000082\",\"name\":\"甄宝\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2051\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"d5e3eb8e76a6f8ef49895f4681967079ea7319bf7998682f5ee5cb7d\",\"updateBy\":\"1\",\"updateDate\":\"2019-07-29 09:37:26\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"zhenb\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"140225200005081811\",\"faceId\":\"5141248c-8e9a-404e-942a-aa593d93e344\",\"facePic\":\"41154b528e3345d4b95c3288e98d2a2a.jpg\",\"timestamp\":1564364246000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-11-04 11:01:56\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12000000660@163.com\",\"gender\":\"\",\"id\":\"4b3cc9e8a3d143ed93201f1ae4ffeb5e\",\"idCard\":\"131002199311072014\",\"loginFlag\":\"1\",\"loginIp\":\"\",\"loginName\":\"yangjl2\",\"mobile\":\"12000000660\",\"name\":\"杨继龙\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2039\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-04 11:01:56\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"yangjl2\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"131002199311072014\",\"faceId\":\"14691533-6967-4976-8f40-2dc88c258c35\",\"facePic\":\"4b3cc9e8a3d143ed93201f1ae4ffeb5e.jpg\",\"timestamp\":1572836516000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-11-19 11:05:14\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"\",\"gender\":\"\",\"id\":\"49f5cc2dc56ff7e77c634dd282c1b6d5\",\"idCard\":\"140104197307280013\",\"loginFlag\":\"1\",\"loginName\":\"zhangxb2\",\"mobile\":\"12000000671\",\"name\":\"张秀斌\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2040\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-19 11:05:14\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"zhangxb2\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"140104197307280013\",\"faceId\":\"d1802237-bf3b-4ebb-920c-aaa61d26ab02\",\"facePic\":\"49f5cc2dc56ff7e77c634dd282c1b6d5.jpg\",\"timestamp\":1574132714000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 15:04:15\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12069283@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"6f32f527c5fc4b96bfd8214cdbe50857\",\"idCard\":\"110108197112156047\",\"loginDate\":\"2019-09-05 15:17:01\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.197.55\",\"loginName\":\"shiqiaofeng\",\"mobile\":\"13082139121\",\"name\":\"史巧凤\",\"nation\":\"\",\"no\":\"12069283\",\"orgId\":\"2011\",\"partyCost\":-1,\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"sort\":-1,\"token\":\"edd9786435f73504e270a04192200780000ff6f0439abe4a91ad4094\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-05 09:54:10\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"shiqiaofeng\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"110108197112156047\",\"faceId\":\"d9f8a6f9-13ac-4e21-8fc0-b21dd4d439b1\",\"facePic\":\"6f32f527c5fc4b96bfd8214cdbe50857.jpg\",\"timestamp\":1572918850000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-25 09:12:11\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000131@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"64e5ae71b9f842f098f506036a650580\",\"idCard\":\"140225198911111832\",\"loginDate\":\"2019-09-02 08:55:15\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.207.15\",\"loginName\":\"sunxb\",\"mobile\":\"12000000131\",\"name\":\"孙晓宾\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2049\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"01cbadbdbd207db0fb80cf15d67f686cc4ed5153a284f67fae6499d2\",\"updateBy\":\"1\",\"updateDate\":\"2019-05-27 17:32:16\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"sunxb\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"140225198911111832\",\"faceId\":\"16a8dd7c-9273-4ae7-87b7-77ba4a5ab906\",\"facePic\":\"64e5ae71b9f842f098f506036a650580.jpg\",\"timestamp\":1558949536000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-10-29 17:18:22\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000607@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"5db662706d5d4708843a4745d0a9c910\",\"idCard\":\"220104196809210045\",\"loginFlag\":\"1\",\"loginIp\":\"\",\"loginName\":\"wangxin\",\"mobile\":\"12000000603\",\"name\":\"王欣\",\"nation\":\"\",\"no\":\"90000607\",\"orgId\":\"2070\",\"partyCost\":-1,\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"sort\":-1,\"token\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-04 09:14:57\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"wangxin\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"220104196809210045\",\"faceId\":\"030f2872-a18c-48f5-9ab1-1df2acd5e7d9\",\"facePic\":\"5db662706d5d4708843a4745d0a9c910.jpg\",\"timestamp\":1572830097000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-11-14 16:49:10\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000661@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"5c30709c1f8e2f0b05df4900c7569108\",\"idCard\":\"130322200102032610\",\"loginFlag\":\"1\",\"loginName\":\"xukm\",\"mobile\":\"15631625170\",\"name\":\"许铠明\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2077\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-14 16:49:10\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"xukm\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"130322200102032610\",\"faceId\":\"6177d37a-a9d3-48b1-8b2a-c08b27116322\",\"facePic\":\"5c30709c1f8e2f0b05df4900c7569108.jpg\",\"timestamp\":1573721350000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-11-14 16:58:34\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"90000672@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"684ffc3fac664b52491d26c8bb87616d\",\"idCard\":\"132801198207282234\",\"loginFlag\":\"1\",\"loginName\":\"chenj\",\"mobile\":\"15369666663\",\"name\":\"陈建\",\"nation\":\"\",\"no\":\"\",\"orgId\":\"2077\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"updateBy\":\"1\",\"updateDate\":\"2019-11-14 16:58:34\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"chenj\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"132801198207282234\",\"faceId\":\"f2fd0367-b4ed-41aa-9b00-58ad4c28a2ff\",\"facePic\":\"684ffc3fac664b52491d26c8bb87616d.jpg\",\"timestamp\":1573721914000},{\"companyId\":\"1\",\"createBy\":\"1\",\"createDate\":\"2019-05-24 15:14:42\",\"delFlag\":\"0\",\"education\":\"\",\"email\":\"12075881@chnenergy.com.cn\",\"gender\":\"\",\"id\":\"7d84c4fa94f14c078643bbf12a7c38b7\",\"idCard\":\"130229199204261813\",\"loginDate\":\"2019-09-04 19:54:35\",\"loginFlag\":\"1\",\"loginIp\":\"10.69.197.55\",\"loginName\":\"chenhaiwang\",\"mobile\":\"15531650025\",\"name\":\"陈海旺\",\"nation\":\"\",\"no\":\"12075881\",\"orgId\":\"2021\",\"password\":\"\",\"phone\":\"\",\"photo\":\"\",\"posCode\":\"\",\"remarks\":\"\",\"residence\":\"\",\"token\":\"ae9594b36bb4be48cb5a542cf754c4969a964ed32f9b976985d05705\",\"updateBy\":\"1\",\"updateDate\":\"2019-05-27 17:23:46\",\"userStatus\":\"0\",\"userType\":\"\",\"activated\":false,\"username\":\"chenhaiwang\",\"userGroup\":\"厂内人员\",\"certificateNum\":\"130229199204261813\",\"faceId\":\"056130f8-5f39-4949-ad5e-a313d2a127bc\",\"facePic\":\"7d84c4fa94f14c078643bbf12a7c38b7.jpg\",\"timestamp\":1558949026000}]";

}
