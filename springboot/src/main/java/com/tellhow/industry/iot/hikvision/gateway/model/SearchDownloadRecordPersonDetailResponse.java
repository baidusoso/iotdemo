package com.tellhow.industry.iot.hikvision.gateway.model;

import java.util.List;

public class SearchDownloadRecordPersonDetailResponse {
    public int total;
    public List<SearchDownloadRecordPersonDetail> list;

    public static class SearchDownloadRecordPersonDetail {
        public String downloadResultId;
        public String personId;
        public String persondownloadResult;
    }
}
