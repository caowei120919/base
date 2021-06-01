package com.datacvg.dimp.bean;

import androidx.annotation.Keep;
import java.io.Serializable;
import java.util.List;


/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 指标实体
 */
@Keep
public class DimensionPositionBean implements Serializable {


    private List<IndexPositionBean> indexPosition;

    public List<IndexPositionBean> getIndexPosition() {
        return indexPosition;
    }

    public void setIndexPosition(List<IndexPositionBean> indexPosition) {
        this.indexPosition = indexPosition;
    }

    public static class IndexPositionBean {
        /**
         * index_id : hsf-total-sales
         * positon_pkid : 826926036743910479839
         * role_pkid :
         * pos_x : 3
         * pos_y : 1
         * size_x : 2
         * size_y : 2
         * update_time : 2021-05-25 17:59:01
         * update_user : 454980024013070447909
         * chart_type : dashboard
         * index_pkid : 810644572793753354098
         * analysis_dimension : 602219233859972617510
         * index_description : 商品总销售金额=商品线上销售金额+商品线下销售金额
         * page_chart_type : dashboard
         * page_analysis_dim : 602219233859972617510
         * sort :
         */

        private String index_id;
        private String positon_pkid;
        private String role_pkid;
        private Integer pos_x;
        private Integer pos_y;
        private String size_x;
        private String size_y;
        private String update_time;
        private String update_user;
        private String chart_type;
        private String index_pkid;
        private String analysis_dimension;
        private String index_description;
        private String page_chart_type;
        private String page_analysis_dim;
        private String sort;
        private IndexChartBean chartBean ;

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getPositon_pkid() {
            return positon_pkid;
        }

        public void setPositon_pkid(String positon_pkid) {
            this.positon_pkid = positon_pkid;
        }

        public String getRole_pkid() {
            return role_pkid;
        }

        public void setRole_pkid(String role_pkid) {
            this.role_pkid = role_pkid;
        }

        public Integer getPos_x() {
            return pos_x;
        }

        public void setPos_x(Integer pos_x) {
            this.pos_x = pos_x;
        }

        public Integer getPos_y() {
            return pos_y;
        }

        public void setPos_y(Integer pos_y) {
            this.pos_y = pos_y;
        }

        public String getSize_x() {
            return size_x;
        }

        public void setSize_x(String size_x) {
            this.size_x = size_x;
        }

        public String getSize_y() {
            return size_y;
        }

        public void setSize_y(String size_y) {
            this.size_y = size_y;
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

        public String getChart_type() {
            return chart_type;
        }

        public void setChart_type(String chart_type) {
            this.chart_type = chart_type;
        }

        public String getIndex_pkid() {
            return index_pkid;
        }

        public void setIndex_pkid(String index_pkid) {
            this.index_pkid = index_pkid;
        }

        public String getAnalysis_dimension() {
            return analysis_dimension;
        }

        public void setAnalysis_dimension(String analysis_dimension) {
            this.analysis_dimension = analysis_dimension;
        }

        public String getIndex_description() {
            return index_description;
        }

        public void setIndex_description(String index_description) {
            this.index_description = index_description;
        }

        public String getPage_chart_type() {
            return page_chart_type;
        }

        public void setPage_chart_type(String page_chart_type) {
            this.page_chart_type = page_chart_type;
        }

        public String getPage_analysis_dim() {
            return page_analysis_dim;
        }

        public void setPage_analysis_dim(String page_analysis_dim) {
            this.page_analysis_dim = page_analysis_dim;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public IndexChartBean getChartBean() {
            return chartBean;
        }

        public void setChartBean(IndexChartBean chartBean) {
            this.chartBean = chartBean;
        }
    }
}
