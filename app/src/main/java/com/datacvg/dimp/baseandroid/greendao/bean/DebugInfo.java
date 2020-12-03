package com.datacvg.dimp.baseandroid.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-24
 * @Description : bug详情实体
 */
@Keep
@Entity
public class DebugInfo {

    @Id(autoincrement = true)
    private Long id ;

    private String debugtype;

    private String debuginfo;

    private String debugtime;

    @Generated(hash = 873858228)
    public DebugInfo(Long id, String debugtype, String debuginfo,
            String debugtime) {
        this.id = id;
        this.debugtype = debugtype;
        this.debuginfo = debuginfo;
        this.debugtime = debugtime;
    }

    @Generated(hash = 1912846593)
    public DebugInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebugtype() {
        return this.debugtype;
    }

    public void setDebugtype(String debugtype) {
        this.debugtype = debugtype;
    }

    public String getDebuginfo() {
        return this.debuginfo;
    }

    public void setDebuginfo(String debuginfo) {
        this.debuginfo = debuginfo;
    }

    public String getDebugtime() {
        return this.debugtime;
    }

    public void setDebugtime(String debugtime) {
        this.debugtime = debugtime;
    }
}
