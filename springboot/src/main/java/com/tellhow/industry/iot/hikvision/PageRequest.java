package com.tellhow.industry.iot.hikvision;

public class PageRequest {
    /**
     * 当前页码
     * 当前页码需大于0（pageNo>0）
     */
    public int pageNo = 1;
    /**
     * 每页记录总数
     * 每页记录展示的数目应大于0，小于等于1000（0<pageSize<=1000）
     */
    public int pageSize = 1000;

    public PageRequest(int pageNo, int pageSize) {
        if (pageNo <= 0 || pageSize <= 0 || pageSize > 1000) throw new IllegalArgumentException();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
