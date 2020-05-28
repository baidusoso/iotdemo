package com.tellhow.industry.iot.gateway.hikvision;

public class GetwayException extends RuntimeException {
    public String code;
    public String msg;

    public GetwayException(String code, String msg) {
        super("code:" + code + " msg:" + msg);
        this.code = code;
        this.msg = msg;
    }

    public GetwayException(String msg) {
        super(msg);
    }
}
