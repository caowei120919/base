package com.datacvg.sempmobile.baseandroid.greendao.bean;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-24
 * @Description :
 */
@Keep
@Entity
public class ThreadInfo {

    @Id(autoincrement = true)
    private long id;

    private String uri;

    @NonNull
    private String tag;
    private long startoffset;
    private long endoffset;
    private long finished;
    @Generated(hash = 406990765)
    public ThreadInfo(long id, String uri, @NonNull String tag, long startoffset,
            long endoffset, long finished) {
        this.id = id;
        this.uri = uri;
        this.tag = tag;
        this.startoffset = startoffset;
        this.endoffset = endoffset;
        this.finished = finished;
    }
    @Generated(hash = 930225280)
    public ThreadInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUri() {
        return this.uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public long getStartoffset() {
        return this.startoffset;
    }
    public void setStartoffset(long startoffset) {
        this.startoffset = startoffset;
    }
    public long getEndoffset() {
        return this.endoffset;
    }
    public void setEndoffset(long endoffset) {
        this.endoffset = endoffset;
    }
    public long getFinished() {
        return this.finished;
    }
    public void setFinished(long finished) {
        this.finished = finished;
    }
}
