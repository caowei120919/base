package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :  大屏详情
 */
@Keep
public class ScreenDetailBean {

    /**
     * screenAttribute : {"animationMode":"circulation","animationTime":300,"animationEffect":"moveLeft"}
     * list : [{"img_name":"全球研发基地","pkid":"586362966873816142806","res_attribute":"{\"stayTime\":10,\"stayUnit\":\"second\",\"loadTime\":5,\"loadTimeUnit\":\"second\",\"animationTime\":300,\"animationEffect\":\"moveRight\"}","res_type":"report","resource_tag":"146789108984050649","screen_id":"586359126890336611085","sort_val":0,"update_time":"2020-10-12 15:05:27","update_user":"149492828209879757"},{"img_name":"全球布局盈利能力","pkid":"586362967396577278890","res_attribute":"{\"stayTime\":10,\"stayUnit\":\"second\",\"loadTime\":5,\"loadTimeUnit\":\"second\",\"animationTime\":300,\"animationEffect\":\"moveRight\"}","res_type":"report","resource_tag":"146789567814168406","screen_id":"586359126890336611085","sort_val":1,"update_time":"2020-10-12 15:05:27","update_user":"149492828209879757"},{"img_name":"screen20201021064851-883","pkid":"849340876190462062671","res_attribute":"{\"stayTime\":300,\"stayUnit\":\"second\",\"loadTime\":20,\"loadTimeUnit\":\"second\",\"animationTime\":300,\"animationEffect\":\"moveRight\"}","res_type":"img","resource_tag":"image_read/849340857490623608874","screen_id":"586359126890336611085","sort_val":2,"update_time":"2020-10-21 18:48:51","update_user":"149492828209879757"}]
     */

    private String screenAttribute;
    private List<ListBean> list;

    public String getScreenAttribute() {
        return screenAttribute;
    }

    public void setScreenAttribute(String screenAttribute) {
        this.screenAttribute = screenAttribute;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * img_name : 全球研发基地
         * pkid : 586362966873816142806
         * res_attribute : {"stayTime":10,"stayUnit":"second","loadTime":5,"loadTimeUnit":"second","animationTime":300,"animationEffect":"moveRight"}
         * res_type : report
         * resource_tag : 146789108984050649
         * screen_id : 586359126890336611085
         * sort_val : 0
         * update_time : 2020-10-12 15:05:27
         * update_user : 149492828209879757
         */

        private String img_name;
        private String pkid;
        private String res_attribute;
        private String res_type;
        private String resource_tag;
        private String screen_id;
        private int sort_val;
        private String update_time;
        private String update_user;

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getPkid() {
            return pkid;
        }

        public void setPkid(String pkid) {
            this.pkid = pkid;
        }

        public String getRes_attribute() {
            return res_attribute;
        }

        public void setRes_attribute(String res_attribute) {
            this.res_attribute = res_attribute;
        }

        public String getRes_type() {
            return res_type;
        }

        public void setRes_type(String res_type) {
            this.res_type = res_type;
        }

        public String getResource_tag() {
            return resource_tag;
        }

        public void setResource_tag(String resource_tag) {
            this.resource_tag = resource_tag;
        }

        public String getScreen_id() {
            return screen_id;
        }

        public void setScreen_id(String screen_id) {
            this.screen_id = screen_id;
        }

        public int getSort_val() {
            return sort_val;
        }

        public void setSort_val(int sort_val) {
            this.sort_val = sort_val;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(String update_user) {
            this.update_user = update_user;
        }
    }
}
