package com.tellhow.industry.iot.system.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class Region {
    private String regionCode;
    @JSONField(serialize = false)
    private transient Region parentRegion;
    @JSONField(serialize = false)
    private String parentRegionCode;
    private String regionName;

    private List<Region> children = new ArrayList<>();

    public Region getParentRegion() {
        return parentRegion;
    }

    public void setParentRegion(Region parentRegion) {
        this.parentRegion = parentRegion;
    }

    public String getParentRegionCode() {
        return parentRegionCode;
    }

    public void setParentRegionCode(String parentRegionCode) {

        this.parentRegionCode = parentRegionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public List<Region> getChildren() {
        return children;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void addChild(Region child) {
        this.children.add(child);
    }
}
