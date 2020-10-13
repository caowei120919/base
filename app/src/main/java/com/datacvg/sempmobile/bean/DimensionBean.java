package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description : 维度
 */
@Keep
public class DimensionBean {
    private int level = 0 ;
    private boolean isOpen = false ;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    private String user_pkid;
    private String flname;
    private String pid;
    private String id;
    private String text;
    private String rid;
    private String value;
    private List<DimensionBean> nodes;

    public String getUser_pkid() {
        return user_pkid;
    }

    public void setUser_pkid(String user_pkid) {
        this.user_pkid = user_pkid;
    }

    public String getFlname() {
        return flname;
    }

    public void setFlname(String flname) {
        this.flname = flname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DimensionBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<DimensionBean> nodes) {
        this.nodes = nodes;
    }
}
