package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-09
 * @Description : 评论实体
 */
@Keep
public class CommentBean {

    /**
     * comment_pkid : 1270927021241573872012
     * parent_id : 0
     * res_id : 42685323854854672887
     * comment_user_id : barry.chen
     * reply_user_id : barry.chen
     * comment_text : 国家科技奖
     * delete_status : F
     * update_time : 2020-12-09 13:53:12
     * comment_username : 测试账号
     * reply_username : 测试账号
     * imageNameList : []
     * texts : ["国家科技奖"]
     * appImageNameList : []
     */

    private String comment_pkid;
    private String parent_id;
    private String res_id;
    private String comment_user_id;
    private String reply_user_id;
    private String comment_text;
    private String delete_status;
    private String update_time;
    private String comment_username;
    private String reply_username;
    private List<String> texts;
    private List<AppImageNameBean> appImageNameList;
    private boolean isCanDelete ;
    private List<CommentBean> childComment =new ArrayList<>();
    private int level = 0 ;
    private boolean hasSpread = false ;

    public boolean isHasSpread() {
        return hasSpread;
    }

    public void setHasSpread(boolean hasSpread) {
        this.hasSpread = hasSpread;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<CommentBean> getChildComment() {
        return childComment;
    }

    public void setChildComment(List<CommentBean> childComment) {
        this.childComment = childComment;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    public String getComment_pkid() {
        return comment_pkid;
    }

    public void setComment_pkid(String comment_pkid) {
        this.comment_pkid = comment_pkid;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(String comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public String getReply_user_id() {
        return reply_user_id;
    }

    public void setReply_user_id(String reply_user_id) {
        this.reply_user_id = reply_user_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getComment_username() {
        return comment_username;
    }

    public void setComment_username(String comment_username) {
        this.comment_username = comment_username;
    }

    public String getReply_username() {
        return reply_username;
    }

    public void setReply_username(String reply_username) {
        this.reply_username = reply_username;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public List<AppImageNameBean> getAppImageNameList() {
        return appImageNameList;
    }

    public void setAppImageNameList(List<AppImageNameBean> appImageNameList) {
        this.appImageNameList = appImageNameList;
    }

    @Keep
    public class AppImageNameBean {

        /**
         * pkid : 1270044484094078483534
         * img_name : fileName=1270044482889317746069&fileType=comment&outPutWay=io
         */

        private String pkid;
        private String img_name;

        public String getPkid() {
            return pkid;
        }

        public void setPkid(String pkid) {
            this.pkid = pkid;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }
    }
}
