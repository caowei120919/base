package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-30
 * @Description : 指标
 */
@Keep
public class IndexBean {

    private List<MyListBean> myList;
    private List<EndResultBean> endResult;

    public List<MyListBean> getMyList() {
        return myList;
    }

    public void setMyList(List<MyListBean> myList) {
        this.myList = myList;
    }

    public List<EndResultBean> getEndResult() {
        return endResult;
    }

    public void setEndResult(List<EndResultBean> endResult) {
        this.endResult = endResult;
    }

    public static class MyListBean {
        /**
         * chart_type : dashboard
         * size_y : 2
         * size_x : 2
         * id : IBI-prl
         * analysis_dimension : TIME
         * title : 人力成本
         * index_type : 1108215038849871042492
         */

        private String chart_type;
        private int size_y;
        private int size_x;
        private String id;
        private String analysis_dimension;
        private String title;
        private String index_type;

        public String getChart_type() {
            return chart_type;
        }

        public void setChart_type(String chart_type) {
            this.chart_type = chart_type;
        }

        public int getSize_y() {
            return size_y;
        }

        public void setSize_y(int size_y) {
            this.size_y = size_y;
        }

        public int getSize_x() {
            return size_x;
        }

        public void setSize_x(int size_x) {
            this.size_x = size_x;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAnalysis_dimension() {
            return analysis_dimension;
        }

        public void setAnalysis_dimension(String analysis_dimension) {
            this.analysis_dimension = analysis_dimension;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIndex_type() {
            return index_type;
        }

        public void setIndex_type(String index_type) {
            this.index_type = index_type;
        }
    }

    public static class EndResultBean {
        /**
         * index_classification_name : 其他
         * list : []
         * index_type : 99
         */

        private String index_classification_name;
        private String index_type;
        private List<EndIndexBean> list;

        public String getIndex_classification_name() {
            return index_classification_name;
        }

        public void setIndex_classification_name(String index_classification_name) {
            this.index_classification_name = index_classification_name;
        }

        public String getIndex_type() {
            return index_type;
        }

        public void setIndex_type(String index_type) {
            this.index_type = index_type;
        }

        public List<EndIndexBean> getList() {
            return list;
        }

        public void setList(List<EndIndexBean> list) {
            this.list = list;
        }
    }

    public static class EndIndexBean {

        /**
         * index_clname : 事业部收入
         * chart_wide : 2
         * index_pkid : 15171776569319093610
         * chart_unit : 元
         * chart_high : 2
         * chart_top_title : 哈哈哈哈收入
         * index_type : 1108110367092326288374
         * chart_bottom_title :
         * index_classification_name : 财务成果
         * index_description : 指标说明：事业部收入
         计算逻辑：项目收入+产品销售毛利+售前收入+运维收入+借调收入
         * chart_type : bar_chart
         * index_id : IBI-inc_dvs
         * analysis_dimension : region
         * chart_end_time : 0
         */

        /**
         * chart_type : dashboard
         * size_y : 2
         * size_x : 2
         * id : IBI-prl
         * analysis_dimension : TIME
         * title : 人力成本
         * index_type : 1108215038849871042492
         */

        private String index_clname;
        private String chart_wide;
        private String index_pkid;
        private String chart_unit;
        private String chart_high;
        private String chart_top_title;
        private String index_type;
        private String chart_bottom_title;
        private String index_classification_name;
        private String index_description;
        private String chart_type;
        private String index_id;
        private String analysis_dimension;
        private String chart_end_time;
        private boolean isSelected = false ;    //是否被选择为我的指标

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getIndex_clname() {
            return index_clname;
        }

        public void setIndex_clname(String index_clname) {
            this.index_clname = index_clname;
        }

        public String getChart_wide() {
            return chart_wide;
        }

        public void setChart_wide(String chart_wide) {
            this.chart_wide = chart_wide;
        }

        public String getIndex_pkid() {
            return index_pkid;
        }

        public void setIndex_pkid(String index_pkid) {
            this.index_pkid = index_pkid;
        }

        public String getChart_unit() {
            return chart_unit;
        }

        public void setChart_unit(String chart_unit) {
            this.chart_unit = chart_unit;
        }

        public String getChart_high() {
            return chart_high;
        }

        public void setChart_high(String chart_high) {
            this.chart_high = chart_high;
        }

        public String getChart_top_title() {
            return chart_top_title;
        }

        public void setChart_top_title(String chart_top_title) {
            this.chart_top_title = chart_top_title;
        }

        public String getIndex_type() {
            return index_type;
        }

        public void setIndex_type(String index_type) {
            this.index_type = index_type;
        }

        public String getChart_bottom_title() {
            return chart_bottom_title;
        }

        public void setChart_bottom_title(String chart_bottom_title) {
            this.chart_bottom_title = chart_bottom_title;
        }

        public String getIndex_classification_name() {
            return index_classification_name;
        }

        public void setIndex_classification_name(String index_classification_name) {
            this.index_classification_name = index_classification_name;
        }

        public String getIndex_description() {
            return index_description;
        }

        public void setIndex_description(String index_description) {
            this.index_description = index_description;
        }

        public String getChart_type() {
            return chart_type;
        }

        public void setChart_type(String chart_type) {
            this.chart_type = chart_type;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getAnalysis_dimension() {
            return analysis_dimension;
        }

        public void setAnalysis_dimension(String analysis_dimension) {
            this.analysis_dimension = analysis_dimension;
        }

        public String getChart_end_time() {
            return chart_end_time;
        }

        public void setChart_end_time(String chart_end_time) {
            this.chart_end_time = chart_end_time;
        }
    }
}
