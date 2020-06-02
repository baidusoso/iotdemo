package com.tellhow.industry.iot.hikvision.person;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tellhow.industry.iot.elasticsearch.ElasticsearchApi;
import com.tellhow.industry.iot.hikvision.BaseApi;
import com.tellhow.industry.iot.hikvision.BaseResponse;
import com.tellhow.industry.iot.hikvision.GatewayException;
import com.tellhow.industry.iot.hikvision.person.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PersonApi extends BaseApi {

    public Person getPersonById(String personId) {
        try {
            BaseResponse<Person> getPersonResponse = post(new TypeReference<BaseResponse<Person>>() {
            }, PersonInterface.PATH_GET_PERSONINFO_BY_PERSONID, JSON.toJSONString(new Person(personId)));
            return getPersonResponse.data;
        } catch (GatewayException gatewayException) {
            if (gatewayException.code != null && "0x14c00002".equals(gatewayException.code.toLowerCase())) {
                return null;
            }
            throw gatewayException;
        }
    }

    public String addPerson(ElasticsearchApi.Account account) {
        AddPersonRequest addPersonRequest = new AddPersonRequest();
        addPersonRequest.personId = account.id;
        addPersonRequest.personName = account.name;
        addPersonRequest.orgIndexCode = account.orgId;
        addPersonRequest.phoneNo = account.mobile;
        addPersonRequest.certificateType = "111";
        addPersonRequest.certificateNo = account.certificateNum;
        BaseResponse<String> getPersonResponse = post(new TypeReference<BaseResponse<String>>() {
        }, PersonInterface.PATH_ADD_PERSON, JSON.toJSONString(addPersonRequest));
        return getPersonResponse.data;
    }

    public String queryDefaultFaceGroup() {
        SearchFaceGroupRequest searchFaceGroupRequest = new SearchFaceGroupRequest();
        searchFaceGroupRequest.name = "default";
        BaseResponse<SearchFaceGroupResponse> searchFaceGroupResponse = post(new TypeReference<BaseResponse<SearchFaceGroupResponse>>() {
        }, PersonInterface.PATH_SEARCH_FACE_GROUP, JSON.toJSONString(searchFaceGroupRequest));
        if (searchFaceGroupResponse.data != null && searchFaceGroupResponse.data.indexCodes != null && searchFaceGroupResponse.data.indexCodes.size() > 0) {
            return searchFaceGroupResponse.data.indexCodes.get(0);
        }
        return null;
    }

    public String addDefaultFaceGroup() {
        AddSingleFaceGroupRequest addSingleFaceGroupRequest = new AddSingleFaceGroupRequest();
        addSingleFaceGroupRequest.name = "default";
        addSingleFaceGroupRequest.description = "default";
        BaseResponse<AddSingleFaceGroupResponse> addSingleFaceGroupResponse = post(new TypeReference<BaseResponse<AddSingleFaceGroupResponse>>() {
        }, PersonInterface.PATH_ADD_FACE_GROUP, JSON.toJSONString(addSingleFaceGroupRequest));
        if (addSingleFaceGroupResponse.data != null) {
            return addSingleFaceGroupResponse.data.indexCode;
        }
        return null;
    }

    public String addFace(String faceGroup, ElasticsearchApi.Account account) {
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.name = account.name;
        //构造人脸
        FacePic facePic = new FacePic();
//        方式一：人脸图片url
        facePic.faceUrl = account.getFacePicUrl();
//        方式二：人脸图片的二进制数据经过Base64编码后的字符串
//        facePic.faceBinaryData = getFaceBinaryData();

        //单个添加人脸请求参数
        AddSingleFaceRequest addSingleFaceRequest = new AddSingleFaceRequest();
        addSingleFaceRequest.faceGroupIndexCode = faceGroup;
        addSingleFaceRequest.faceInfo = faceInfo;
        addSingleFaceRequest.facePic = facePic;

        BaseResponse<AddSingleFaceResponse> addSingleFaceGroupResponse = post(new TypeReference<BaseResponse<AddSingleFaceResponse>>() {
        }, PersonInterface.PATH_ADD_FACE, JSON.toJSONString(addSingleFaceRequest));
        if (addSingleFaceGroupResponse.data != null) {
            return addSingleFaceGroupResponse.data.indexCode;
        }
        return null;
    }

    String getFaceBinaryData(ElasticsearchApi.Account account) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(account.getFacePicUrl());

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                responseEntity.writeTo(byteArrayOutputStream);
                return Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        return null;
    }

//    public String addFaceResource(ElasticsearchApi.Account account) {
//        AddSinglePersonFaceRequest addSinglePersonFaceRequest = new AddSinglePersonFaceRequest();
//        addSinglePersonFaceRequest.personId = account.id;
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet(account.getFacePicUrl());
//
//        // 响应模型
//        CloseableHttpResponse response = null;
//        try {
//            // 由客户端执行(发送)Get请求
//            response = httpClient.execute(httpGet);
//            // 从响应模型中获取响应实体
//            HttpEntity responseEntity = response.getEntity();
//            if (response.getStatusLine().getStatusCode() == 200) {
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                responseEntity.writeTo(byteArrayOutputStream);
//                addSinglePersonFaceRequest.faceData = Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 释放资源
//                if (httpClient != null) {
//                    httpClient.close();
//                }
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (addSinglePersonFaceRequest.faceData == null) {
//            throw new GatewayException(account.id + "获取人脸数据失败：" + account.getFacePicUrl());
//        }
//
//        BaseResponse<AddSinglePersonFaceResponse> getPersonResponse = post(new TypeReference<BaseResponse<AddSinglePersonFaceResponse>>() {
//        }, PersonInterface.PATH_ADD_FACE, JSON.toJSONString(addSinglePersonFaceRequest));
//        if (getPersonResponse.data == null || getPersonResponse.data.faceId == null) {
//            throw new GatewayException(account.id + "添加人脸数据失败：" + account.getFacePicUrl());
//        }
//        return getPersonResponse.data.faceId;
//    }
}
