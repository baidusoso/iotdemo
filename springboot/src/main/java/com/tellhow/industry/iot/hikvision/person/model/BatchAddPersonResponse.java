package com.tellhow.industry.iot.hikvision.person.model;

import java.util.List;

public class BatchAddPersonResponse {
    public List<Success> successes;
    public List<Failure> failures;

    public static class Success {
        public int clientId;
        public String personId;
    }

    public static class Failure {
        public int clientId;
        public String code;
        public String msg;
    }
}
