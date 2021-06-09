package com.datacvg.dimp.bean;

import android.text.TextUtils;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-10
 * @Description :
 */
@Keep
public class IndexChartBean implements Serializable {
    /**
     * index_id : st-maoli
     * index_pkid : 466215280368414856808
     * name : 毛利
     * index_description : 毛利=收入*0.35
     （测试使用）
     * index_default_color : #FFCE00
     * chart_unit : 万元
     * chart_type : bullet_map
     * chart_top_title :
     * chart_bottom_title : 同比增长率
     * chart_wide : 2
     * chart_high : 2
     * chart_end_time : 0
     * chart_range : 2
     * chart_default_valtype : ddb1
     * chart_bottom_valtype : ddb2
     * index_threshold_type : 1
     * index_threshold_value :
     * chart_bottom2_valtype : ddb3
     * value_unit : 万元
     * value_mnt : 10000
     * value_fmt : ###,###.##
     * mnt_description : 万
     * thresholdUnit : 万元
     * timeval : 202104
     * index_data : 89.46
     * orgdimension : 14860367656855969470
     * fudimension : 118306192070461277956
     * pdimension : null
     * thresholdMinimumName : 保底
     * thresholdTargetName : 目标
     * thresholdChallengeName : 挑战
     * thresholdMinimum :
     * thresholdTarget : 150
     * thresholdChallenge :
     * index_data_initial : 894636.05
     * bottom_value : 170.79
     * bottom2_value : 93.94
     * chart_data : [{"value_type":"actual","value_data":"89.46","value_id":"ddb1","value_name":"当前值"},{"value_type":"same_period","value_data":"170.79","value_id":"ddb2","value_name":"同期"},{"value_type":"plan","value_data":"93.94","value_id":"ddb3","value_name":"目标"}]
     */

    private String index_id;
    private String index_pkid;
    private String name;
    private String index_description;
    private String index_default_color;
    private String chart_unit;
    private String chart_type;
    private String chart_top_title;
    private String chart_bottom_title;
    private String chart_wide;
    private String chart_high;
    private String chart_end_time;
    private String chart_range;
    private String chart_default_valtype;
    private String chart_bottom_valtype;
    private String index_threshold_type;
    private String index_threshold_value;
    private String chart_bottom2_valtype;
    private String value_unit;
    private String value_mnt;
    private String value_fmt;
    private String mnt_description;
    private String thresholdUnit;
    private Integer timeval;
    private String index_data;
    private String orgdimension;
    private String fudimension;
    private String pdimension;
    private String thresholdMinimumName;
    private String thresholdTargetName;
    private String thresholdChallengeName;
    private String thresholdMinimum;
    private String thresholdTarget;
    private String thresholdChallenge;
    private Double index_data_initial;
    private String bottom_value;
    private String bottom2_value;
    private List<ChartDataBean> chart_data;
    private String option ;
    private List<String> colorArr;
    private List<ThresholdArrBean> thresholdArr;

    private String index_clname;
    private String page_chart_type;
    private String index_type;
    private String tflag;
    private String index_flname ;
    private String threshold_flag;
    private String analysis_dimension;
    private String index_classification_name;
    private Boolean isSelected = false ;

    public String getIndex_clname() {
        return index_clname;
    }

    public void setIndex_clname(String index_clname) {
        this.index_clname = index_clname;
    }

    public String getPage_chart_type() {
        return page_chart_type;
    }

    public void setPage_chart_type(String page_chart_type) {
        this.page_chart_type = page_chart_type;
    }

    public String getIndex_type() {
        return index_type;
    }

    public void setIndex_type(String index_type) {
        this.index_type = index_type;
    }

    public String getTflag() {
        return tflag;
    }

    public void setTflag(String tflag) {
        this.tflag = tflag;
    }

    public String getIndex_flname() {
        return index_flname;
    }

    public void setIndex_flname(String index_flname) {
        this.index_flname = index_flname;
    }

    public String getThreshold_flag() {
        return threshold_flag;
    }

    public void setThreshold_flag(String threshold_flag) {
        this.threshold_flag = threshold_flag;
    }

    public String getAnalysis_dimension() {
        return analysis_dimension;
    }

    public void setAnalysis_dimension(String analysis_dimension) {
        this.analysis_dimension = analysis_dimension;
    }

    public String getIndex_classification_name() {
        return index_classification_name;
    }

    public void setIndex_classification_name(String index_classification_name) {
        this.index_classification_name = index_classification_name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public static class ThresholdArrBean implements Serializable{
        /**
         * threshold_value : 30.34
         * type : 1
         */

        private String threshold_value;
        private String type;
        private String scaleStart ;
        private String scaleEnd ;

        public String getScaleStart() {
            return scaleStart;
        }

        public void setScaleStart(String scaleStart) {
            this.scaleStart = scaleStart;
        }

        public String getScaleEnd() {
            return scaleEnd;
        }

        public void setScaleEnd(String scaleEnd) {
            this.scaleEnd = scaleEnd;
        }

        public String getThreshold_value() {
            return threshold_value;
        }

        public void setThreshold_value(String threshold_value) {
            this.threshold_value = threshold_value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ChartDataBean implements Serializable{
        /**
         * value_type : actual
         * value_data : 89.46
         * value_id : ddb1
         * value_name : 当前值
         */

        private String value_type;
        private String value_data;
        private String value_id;
        private String value_name;

        public String getValue_type() {
            return value_type;
        }

        public void setValue_type(String value_type) {
            this.value_type = value_type;
        }

        public String getValue_data() {
            return value_data;
        }

        public void setValue_data(String value_data) {
            this.value_data = value_data;
        }

        public String getValue_id() {
            return value_id;
        }

        public void setValue_id(String value_id) {
            this.value_id = value_id;
        }

        public String getValue_name() {
            return value_name;
        }

        public void setValue_name(String value_name) {
            this.value_name = value_name;
        }
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public String getIndex_pkid() {
        return index_pkid;
    }

    public void setIndex_pkid(String index_pkid) {
        this.index_pkid = index_pkid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex_description() {
        return index_description;
    }

    public void setIndex_description(String index_description) {
        this.index_description = index_description;
    }

    public String getIndex_default_color() {
        return index_default_color;
    }

    public void setIndex_default_color(String index_default_color) {
        this.index_default_color = index_default_color;
    }

    public String getChart_unit() {
        return chart_unit;
    }

    public void setChart_unit(String chart_unit) {
        this.chart_unit = chart_unit;
    }

    public String getChart_type() {
        return chart_type;
    }

    public void setChart_type(String chart_type) {
        this.chart_type = chart_type;
    }

    public String getChart_top_title() {
        return chart_top_title;
    }

    public void setChart_top_title(String chart_top_title) {
        this.chart_top_title = chart_top_title;
    }

    public String getChart_bottom_title() {
        return chart_bottom_title;
    }

    public void setChart_bottom_title(String chart_bottom_title) {
        this.chart_bottom_title = chart_bottom_title;
    }

    public String getChart_wide() {
        return chart_wide;
    }

    public void setChart_wide(String chart_wide) {
        this.chart_wide = chart_wide;
    }

    public String getChart_high() {
        return chart_high;
    }

    public void setChart_high(String chart_high) {
        this.chart_high = chart_high;
    }

    public String getChart_end_time() {
        return chart_end_time;
    }

    public void setChart_end_time(String chart_end_time) {
        this.chart_end_time = chart_end_time;
    }

    public String getChart_range() {
        return chart_range;
    }

    public void setChart_range(String chart_range) {
        this.chart_range = chart_range;
    }

    public String getChart_default_valtype() {
        return chart_default_valtype;
    }

    public void setChart_default_valtype(String chart_default_valtype) {
        this.chart_default_valtype = chart_default_valtype;
    }

    public String getChart_bottom_valtype() {
        return chart_bottom_valtype;
    }

    public void setChart_bottom_valtype(String chart_bottom_valtype) {
        this.chart_bottom_valtype = chart_bottom_valtype;
    }

    public String getIndex_threshold_type() {
        return index_threshold_type;
    }

    public void setIndex_threshold_type(String index_threshold_type) {
        this.index_threshold_type = index_threshold_type;
    }

    public String getIndex_threshold_value() {
        return index_threshold_value;
    }

    public void setIndex_threshold_value(String index_threshold_value) {
        this.index_threshold_value = index_threshold_value;
    }

    public String getChart_bottom2_valtype() {
        return chart_bottom2_valtype;
    }

    public void setChart_bottom2_valtype(String chart_bottom2_valtype) {
        this.chart_bottom2_valtype = chart_bottom2_valtype;
    }

    public String getValue_unit() {
        return value_unit;
    }

    public void setValue_unit(String value_unit) {
        this.value_unit = value_unit;
    }

    public String getValue_mnt() {
        return value_mnt;
    }

    public void setValue_mnt(String value_mnt) {
        this.value_mnt = value_mnt;
    }

    public String getValue_fmt() {
        return value_fmt;
    }

    public void setValue_fmt(String value_fmt) {
        this.value_fmt = value_fmt;
    }

    public String getMnt_description() {
        return mnt_description;
    }

    public void setMnt_description(String mnt_description) {
        this.mnt_description = mnt_description;
    }

    public String getThresholdUnit() {
        return thresholdUnit;
    }

    public void setThresholdUnit(String thresholdUnit) {
        this.thresholdUnit = thresholdUnit;
    }

    public Integer getTimeval() {
        return timeval;
    }

    public void setTimeval(Integer timeval) {
        this.timeval = timeval;
    }

    public String getIndex_data() {
        if(TextUtils.isEmpty(index_data)){
            return "" ;
        }
        return index_data.replaceAll(",","");
    }

    public void setIndex_data(String index_data) {
        this.index_data = index_data;
    }

    public String getOrgdimension() {
        return orgdimension;
    }

    public void setOrgdimension(String orgdimension) {
        this.orgdimension = orgdimension;
    }

    public String getFudimension() {
        return fudimension;
    }

    public void setFudimension(String fudimension) {
        this.fudimension = fudimension;
    }

    public String getPdimension() {
        return pdimension;
    }

    public void setPdimension(String pdimension) {
        this.pdimension = pdimension;
    }

    public String getThresholdMinimumName() {
        return thresholdMinimumName;
    }

    public void setThresholdMinimumName(String thresholdMinimumName) {
        this.thresholdMinimumName = thresholdMinimumName;
    }

    public String getThresholdTargetName() {
        return thresholdTargetName;
    }

    public void setThresholdTargetName(String thresholdTargetName) {
        this.thresholdTargetName = thresholdTargetName;
    }

    public String getThresholdChallengeName() {
        return thresholdChallengeName;
    }

    public void setThresholdChallengeName(String thresholdChallengeName) {
        this.thresholdChallengeName = thresholdChallengeName;
    }

    public String getThresholdMinimum() {
        return thresholdMinimum;
    }

    public void setThresholdMinimum(String thresholdMinimum) {
        this.thresholdMinimum = thresholdMinimum;
    }

    public String getThresholdTarget() {
        return thresholdTarget;
    }

    public void setThresholdTarget(String thresholdTarget) {
        this.thresholdTarget = thresholdTarget;
    }

    public String getThresholdChallenge() {
        return thresholdChallenge;
    }

    public void setThresholdChallenge(String thresholdChallenge) {
        this.thresholdChallenge = thresholdChallenge;
    }

    public Double getIndex_data_initial() {
        return index_data_initial;
    }

    public void setIndex_data_initial(Double index_data_initial) {
        this.index_data_initial = index_data_initial;
    }

    public String getBottom_value() {
        return bottom_value;
    }

    public void setBottom_value(String bottom_value) {
        this.bottom_value = bottom_value;
    }

    public String getBottom2_value() {
        return bottom2_value;
    }

    public void setBottom2_value(String bottom2_value) {
        this.bottom2_value = bottom2_value;
    }

    public List<ChartDataBean> getChart_data() {
        return chart_data;
    }

    public void setChart_data(List<ChartDataBean> chart_data) {
        this.chart_data = chart_data;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<String> getColorArr() {
        return colorArr;
    }

    public void setColorArr(List<String> colorArr) {
        this.colorArr = colorArr;
    }

    public List<ThresholdArrBean> getThresholdArr() {
        return thresholdArr;
    }

    public void setThresholdArr(List<ThresholdArrBean> thresholdArr) {
        this.thresholdArr = thresholdArr;
    }


}
