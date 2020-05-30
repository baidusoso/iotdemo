package com.tellhow.industry.iot.hikvision;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.tellhow.industry.iot.system.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

        if (pathSegment.equals(GatewayInterface.PATH_GET_GATEWAY_LIST)) {
            result = MOACK_GATEWAY;
        }
        if (pathSegment.equals(GatewayInterface.PATH_GET_DOOR_LIST)) {
            result = MOACK_DOOR;
        }
        logger.debug(result);
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

    protected interface GatewayInterface {
        String PATH_GET_GATEWAY_LIST = "/api/resource/v1/acsDevice/acsDeviceList";
        String PATH_GET_DOOR_LIST = "/api/resource/v1/acsDoor/advance/acsDoorList";
    }

    static final String MOACK_GATEWAY = "{\"code\":\"0\",\"msg\":\"SUCCESS\",\"data\":{\"total\":176,\"pageNo\":1,\"pageSize\":1000,\"list\":[{\"acsDevIndexCode\":\"36dff7cf0ddf42fe897aa760416e2a55\",\"acsDevName\":\"1号机一次风机变频间\",\"regionIndexCode\":\"31deda20-635a-4557-8f90-74837672b64e\",\"treatyType\":\"hiksdk_net\",\"acsDevTypeDesc\":\"DS-K1T60HZ-DC\",\"acsDevTypeCode\":\"201933568\",\"acsDevTypeName\":null,\"acsDevIp\":\"192.168.13.19\",\"acsDevPort\":\"8000\",\"acsDevCode\":null,\"createTime\":\"2019-10-30T16:29:10.630+08:00\",\"updateTime\":\"2019-11-05T17:12:30.509+08:00\"},{\"acsDevIndexCode\":\"122cc79e81c849998da75ad792a0f052\",\"acsDevName\":\"1号炉等离子整流柜配电柜间北一\",\"regionIndexCode\":\"379ed963-42fd-461c-b7fd-0190156101c3\",\"treatyType\":\"hiksdk_net\",\"acsDevTypeDesc\":\"DS-K1T60HZ-DC\",\"acsDevTypeCode\":\"201933568\",\"acsDevTypeName\":null,\"acsDevIp\":\"192.168.13.20\",\"acsDevPort\":\"8000\",\"acsDevCode\":null,\"createTime\":\"2019-10-30T16:32:35.822+08:00\",\"updateTime\":\"2019-11-09T08:14:44.093+08:00\"},{\"acsDevIndexCode\":\"6f0642c437cc4b89a932645ab4df8c4d\",\"acsDevName\":\"1号炉等离子整流柜配电柜间中二\",\"regionIndexCode\":\"1608faa6-831e-44d6-a876-fe11300f2adc\",\"treatyType\":\"hiksdk_net\",\"acsDevTypeDesc\":\"DS-K1T60HZ-DC\",\"acsDevTypeCode\":\"201933568\",\"acsDevTypeName\":null,\"acsDevIp\":\"192.168.13.21\",\"acsDevPort\":\"8000\",\"acsDevCode\":null,\"createTime\":\"2019-10-30T16:34:04.828+08:00\",\"updateTime\":\"2019-11-10T15:44:11.292+08:00\"},{\"acsDevIndexCode\":\"a3a2daf4b5dc449096ba5844e29c865a\",\"acsDevName\":\"1号炉等离子整流柜配电柜间南三\",\"regionIndexCode\":\"7b708648-18eb-46dd-9ab0-fceec9f7fbf5\",\"treatyType\":\"hiksdk_net\",\"acsDevTypeDesc\":\"DS-K1T60HZ-DC\",\"acsDevTypeCode\":\"201933568\",\"acsDevTypeName\":null,\"acsDevIp\":\"192.168.13.22\",\"acsDevPort\":\"8000\",\"acsDevCode\":null,\"createTime\":\"2019-10-30T16:36:39.575+08:00\",\"updateTime\":\"2019-11-05T18:22:31.913+08:00\"}]}}";
    static final String MOACK_DOOR = "{\"code\":\"0\",\"msg\":\"SUCCESS\",\"data\":{\"total\":181,\"pageNo\":1,\"pageSize\":1000,\"list\":[{\"doorIndexCode\":\"0e0a7aa376454873a227e8f8f6f97d1e\",\"doorName\":\"1号机一次风机变频间_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"31deda20-635a-4557-8f90-74837672b64e\",\"acsDevIndexCode\":\"36dff7cf0ddf42fe897aa760416e2a55\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-30T16:29:28.528+08:00\",\"updateTime\":\"2019-10-30T16:29:28.562+08:00\"},{\"doorIndexCode\":\"61a327b86ee94545a91b394e75a2953f\",\"doorName\":\"1号炉等离子整流柜配电柜间 北一_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"379ed963-42fd-461c-b7fd-0190156101c3\",\"acsDevIndexCode\":\"122cc79e81c849998da75ad792a0f052\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-30T16:32:47.992+08:00\",\"updateTime\":\"2019-10-30T16:32:48.050+08:00\"},{\"doorIndexCode\":\"5aa3e82b3991452aa126a9e05c2c12e8\",\"doorName\":\"1号炉等离子整流柜配电柜间 中二_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"1608faa6-831e-44d6-a876-fe11300f2adc\",\"acsDevIndexCode\":\"6f0642c437cc4b89a932645ab4df8c4d\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-30T16:34:24.272+08:00\",\"updateTime\":\"2019-10-30T16:34:24.307+08:00\"},{\"doorIndexCode\":\"0c3518c8f15b4b9da7242d32f7034dc7\",\"doorName\":\"1号炉等离子整流柜配电柜间 南三_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"7b708648-18eb-46dd-9ab0-fceec9f7fbf5\",\"acsDevIndexCode\":\"a3a2daf4b5dc449096ba5844e29c865a\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-30T16:36:55.129+08:00\",\"updateTime\":\"2019-10-30T16:36:55.196+08:00\"},{\"doorIndexCode\":\"8ed6148ec0aa442aa1d435478e9afb0d\",\"doorName\":\"水汽取样分析仪表间_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"6d0d162b-00a2-46d8-b52d-da57a441dca0\",\"acsDevIndexCode\":\"457958abeef34a5c8433c36b6f9560d9\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-30T17:39:01.154+08:00\",\"updateTime\":\"2019-10-30T17:39:01.177+08:00\"},{\"doorIndexCode\":\"5a629ae9579d427aac31b442fdd87022\",\"doorName\":\"1号炉DCS电子间南三_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"9511749a-1fcc-4101-aaea-5d50c8ed04d6\",\"acsDevIndexCode\":\"c392c1d82aa44fd58ebdebffccd04648\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-10-31T10:45:53.659+08:00\",\"updateTime\":\"2019-10-31T10:45:53.700+08:00\"}]}}";
}
