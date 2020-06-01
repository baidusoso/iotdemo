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

//        if (pathSegment.equals(GatewayInterface.PATH_GET_GATEWAY_LIST)) {
//            result = MOACK_GATEWAY;
//        }
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

    static final String MOACK_DOOR = "{\n" +
            "    \"code\": \"0\",\n" +
            "    \"msg\": \"ok\",\n" +
            "    \"data\": {\n" +
            "        \"total\": 37,\n" +
            "        \"pageNo\": 1,\n" +
            "        \"pageSize\": 2,\n" +
            "        \"list\": [{\"doorIndexCode\":\"4308e41320944407ba6514791f938ba1\",\"doorName\":\"东二门中间口人员通道_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"cf1311e43e554f70b7c7d061887e222d\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.867+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"821afca54b3344c5a5c6ce38fab27397\",\"doorName\":\"东二门中间口人员通道_门2\",\"doorNo\":\"2\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"cf1311e43e554f70b7c7d061887e222d\",\"channelType\":\"door\",\"channelNo\":\"2\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.866+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"7a0c3198732f42e599cd20ca031783b4\",\"doorName\":\"东二门北口人员通道_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"cfd6ea7e230f42c685be0fe95ed225b8\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.865+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"4bde5d37bb734096b8554f4cf5cac08b\",\"doorName\":\"东二门北口人员通道_门2\",\"doorNo\":\"2\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"cfd6ea7e230f42c685be0fe95ed225b8\",\"channelType\":\"door\",\"channelNo\":\"2\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.864+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"d10502b319b5435ca1a841e6085e16e6\",\"doorName\":\"东二门南口人员通道_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"1997b995991a4db6999bbad7549bda1e\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.869+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"b03d85b6b1474228ade6f924e1b93584\",\"doorName\":\"东二门南口人员通道_门2\",\"doorNo\":\"2\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"1997b995991a4db6999bbad7549bda1e\",\"channelType\":\"door\",\"channelNo\":\"2\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T17:47:43.868+08:00\",\"updateTime\":\"2019-11-08T17:47:43.928+08:00\"},{\"doorIndexCode\":\"b31856333fe541f888d29a0b6dd2bf13\",\"doorName\":\"东二门中人脸识别出_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"b99ae0616cc44816ad96145674384719\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.636+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"c0b04585c17a425c8b255ad347910514\",\"doorName\":\"东二门人脸识别中间进_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"7c9a3fa85bc644bf9fd2309c2cdcd001\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.635+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"268c3733f5504004b24dc809405ad1be\",\"doorName\":\"东二门人脸识别北出_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"f5169c2ff7034844b3563fadadba9a68\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.634+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"f50137af98fe47a488c0fb8440da9955\",\"doorName\":\"东二门人脸识别南出_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"5f745861b9044b6397a5d60436c78906\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.638+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"47ee16931ed94723aa1b40ec0f7f9bfb\",\"doorName\":\"东二门人脸识别南进_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"5d3f43a6aa784d668020461e49182491\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.637+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"26d6717c9cf5429d9e5b401c89c03eee\",\"doorName\":\"东二门北人脸识别进_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"a567669b-bfc5-4931-8049-ae6fb250c075\",\"acsDevIndexCode\":\"4267fe097c0c457f9f5bef6cdf13c816\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-08T20:09:19.639+08:00\",\"updateTime\":\"2019-11-08T20:09:19.705+08:00\"},{\"doorIndexCode\":\"96eb79fa69634461a543653d9b834eee\",\"doorName\":\"2号汽机 公用电子间门禁_门1\",\"doorNo\":\"1\",\"regionIndexCode\":\"e65e3834-9bcb-46d5-bc70-b7ef59e1e522\",\"acsDevIndexCode\":\"ee5c4297b27347de8d69d20d7af5ff08\",\"channelType\":\"door\",\"channelNo\":\"1\",\"installLocation\":null,\"remark\":null,\"createTime\":\"2019-11-09T09:02:16.889+08:00\",\"updateTime\":\"2019-11-09T09:02:16.942+08:00\"}]}}";
}
