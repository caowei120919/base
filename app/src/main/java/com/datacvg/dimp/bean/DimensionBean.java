package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description : 维度
 */
@Keep
public class DimensionBean {
        private String id;
        private String text;
        private String flname;
        private String pid;
        private String rid;
        private String value;
        private String defaultkey;
        private int res_level;
        private int res_level_sort;
        private List<DimensionBean> nodes;

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

        public String getDefaultkey() {
            return defaultkey;
        }

        public void setDefaultkey(String defaultkey) {
            this.defaultkey = defaultkey;
        }

        public int getRes_level() {
            return res_level;
        }

        public void setRes_level(int res_level) {
            this.res_level = res_level;
        }

    public List<DimensionBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<DimensionBean> nodes) {
        this.nodes = nodes;
    }

    public int getRes_level_sort() {
            return res_level_sort;
        }

        public void setRes_level_sort(int res_level_sort) {
            this.res_level_sort = res_level_sort;
        }
}
