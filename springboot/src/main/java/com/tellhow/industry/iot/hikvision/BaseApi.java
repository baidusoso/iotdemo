package com.tellhow.industry.iot.hikvision;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {

    private Logger logger = LoggerFactory.getLogger(BaseApi.class);

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
        logger.debug("body:" + body);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE_JSON, null);

//        if (pathSegment.equals(GatewayInterface.PATH_GET_GATEWAY_LIST)) {
//            result = MOACK_GATEWAY;
//        }
//        if (pathSegment.equals(GatewayInterface.PATH_GET_DOOR_LIST)) {
//            result = MOACK_DOOR;
//        }

        logger.debug("result:" + result);
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

    protected interface PersonInterface {
        String PATH_GET_PERSONINFO_BY_PERSONID = "/api/resource/v1/person/personId/personInfo";
        String PATH_ADD_PERSON = "/api/resource/v1/person/single/add";
        String PATH_ADD_FACE = "/api/frs/v1/face/single/addition";
        String PATH_SEARCH_FACE_GROUP = "/api/frs/v1/face/group";
        String PATH_ADD_FACE_GROUP = "/api/frs/v1/face/group/single/addition";
    }

    protected interface RegionInterface {
        String PATH_GET_REGION_LIST = "/api/resource/v1/regions";
    }

    protected interface GatewayInterface {
        String PATH_GET_GATEWAY_LIST = "/api/resource/v1/acsDevice/acsDeviceList";
        String PATH_GET_DOOR_LIST = "/api/resource/v1/acsDoor/advance/acsDoorList";
        String PATH_CREATE_AUTH_DOWNLOAD_TASK = "/api/acps/v1/authDownload/task/addition";
        String PATH_ADD_AUTH_DOWNLOAD_DATA = "/api/acps/v1/authDownload/data/addition";
        String PATH_START_AUTH_DOWNLOAD_TASK = "/api/acps/v1/authDownload/task/start";
        String PATH_QUERY_AUTH_DOWNLOAD_TASK_PROGRESS = "/api/acps/v1/authDownload/task/progress";
    }

}
