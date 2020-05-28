package com.tellhow.industry.iot.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Org implements Serializable {
    private String orgCode;
    private String orgName;
    private List<Org> children = new ArrayList<>();

    public String getOrgCode() {
        return orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void addChild(Org child) {
        this.children.add(child);
    }
}
