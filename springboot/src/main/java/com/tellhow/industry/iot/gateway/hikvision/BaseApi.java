package com.tellhow.industry.iot.gateway.hikvision;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "192.168.11.11:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "24238186"; // 秘钥appkey
        ArtemisConfig.appSecret = "SZ9qba6gOajOjZJ4wd03";// 秘钥appSecret
    }

    /**
     * STEP2：设置OpenAPI接口的上下文
     */
    private static final String ARTEMIS_PATH = "/artemis";

    /**
     * STEP3：设置PATH
     */
    Map<String, String> path = new HashMap<>(2);

    /**
     * STEP4：设置参数提交方式
     */
    private static final String CONTENT_TYPE_JSON = "application/json";

    protected <T> BaseResponse<T> post(TypeReference<BaseResponse<T>> typeReference, String pathSegment, String body) {
        if (pathSegment == null || !pathSegment.startsWith("/")) {
            throw new IllegalArgumentException();
        }
        this.path.put("https://", ARTEMIS_PATH + pathSegment);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON, null);

        //通过fastjson把【响应结果】转为对象
        BaseResponse<T> response = JSON.parseObject(result, typeReference);
        if (response == null) {
            throw new GatewayException(BaseResponse.ERR_NO_DATA);
        }
        if (!"0".equals(response.code)) {
            throw new GatewayException(response.code, response.msg);
        }
        return response;
    }


    protected interface OrgInterface {
        String PATH_GET_ROOT = "/api/resource/v1/org/rootOrg";
        String PATH_GET_ORG_LIST = "/api/resource/v1/org/orgList";
    }

    protected interface RegionInterface {
        String PATH_GET_REGION_LIST = "/api/resource/v1/regions";
    }
}
