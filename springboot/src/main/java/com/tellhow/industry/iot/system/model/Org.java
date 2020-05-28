package com.tellhow.industry.iot.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Org implements Serializable {
    private String orgCode;
    private transient Org parentOrg;
    private String parentOrgCode;
    private String orgName;

    private List<Org> children = new ArrayList<>();

    public Org getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Org parentOrg) {
        this.parentOrg = parentOrg;
    }

    public String getParentOrgCode() {
        return parentOrgCode;
    }

    public void setParentOrgCode(String parentOrgCode) {

        this.parentOrgCode = parentOrgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void addChild(Org child) {
        this.children.add(child);
    }
}
