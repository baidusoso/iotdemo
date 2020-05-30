package com.tellhow.industry.iot.hikvision;

public class GatewayException extends RuntimeException {
    public String code;
    public String msg;

    public GatewayException(String code, String msg) {
        super("code:" + code + " msg:" + msg);
        this.code = code;
        this.msg = msg;
    }

    public GatewayException(String msg) {
        super(msg);
    }
}
