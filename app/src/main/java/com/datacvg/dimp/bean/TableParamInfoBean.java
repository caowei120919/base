package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description : 报表参数实体对象
 */
@Keep
public class TableParamInfoBean implements Serializable {


    /**
     * controlName : 时间参数
     * controlParamId :
     * controlPkid : 455412634716365689027
     * controlType : TIME
     * dataSource : [{"endTime":"2021","relativeEnd":"1","relativeStart":"0","startTime":"2019","timeFormat":"yyyy","timeType":"POINTER","timeUnit":"TIME_YEAR"}]
     * reportResId : 852944310754622864773
     * selectBizKey : reportTime
     * selectType :
     * sortVal : 301
     */

    private String controlName;
    private String controlParamId;
    private String controlPkid;
    private String controlType;
    private List<DataSourceBean> dataSource;
    private String reportResId;
    private String selectBizKey;
    private String selectType;
    private String selectionType;
    private String controlDispalyType;

    public String getControlDispalyType() {
        return controlDispalyType;
    }

    public void setControlDispalyType(String controlDispalyType) {
        this.controlDispalyType = controlDispalyType;
    }

    public String getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    private Integer sortVal;

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlParamId() {
        return controlParamId;
    }

    public void setControlParamId(String controlParamId) {
        this.controlParamId = controlParamId;
    }

    public String getControlPkid() {
        return controlPkid;
    }

    public void setControlPkid(String controlPkid) {
        this.controlPkid = controlPkid;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public List<DataSourceBean> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<DataSourceBean> dataSource) {
        this.dataSource = dataSource;
    }

    public String getReportResId() {
        return reportResId;
    }

    public void setReportResId(String reportResId) {
        this.reportResId = reportResId;
    }

    public String getSelectBizKey() {
        return selectBizKey;
    }

    public void setSelectBizKey(String selectBizKey) {
        this.selectBizKey = selectBizKey;
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public Integer getSortVal() {
        return sortVal;
    }

    public void setSortVal(Integer sortVal) {
        this.sortVal = sortVal;
    }

    @Keep
    public static class DataSourceBean implements Serializable{
        /**
         * endTime : 2021
         * relativeEnd : 1
         * relativeStart : 0
         * startTime : 2019
         * timeFormat : yyyy
         * timeType : POINTER
         * timeUnit : TIME_YEAR
         */


        private String endTime;
        private String relativeEnd;
        private String relativeStart;
        private String startTime;
        private String timeFormat;
        private String timeType;
        private String timeUnit;
        /**
         * res_level_sort : 0
         * expires : false
         * create_time : 2019-03-20 10:40:02
         * d_res_showtype : TREE
         * d_res_id : 14860367656855969470
         * d_res_flname : DataCVG
         * d_res_abb : Datacvg
         * d_res_type : user_org
         * d_res_value : DATACVG
         * d_res_clname : 数聚股份
         * d_res_description :
         * d_res_etime : 21000101
         * d_res_pkid : 11860367658010303334
         * d_res_rootid : 965332601103451593621
         * update_time : 2021-05-31 07:21:59
         * update_user : 10000000
         * d_res_parentid : 965332601103451593621
         * create_user : 10000000
         * d_res_stime : 20000101
         * res_level : 0
         * status : T
         */

        private Integer res_level_sort;
        private Boolean expires;
        private String create_time;
        private String d_res_showtype;
        private String d_res_id;
        private String d_res_flname;
        private String d_res_abb;
        private String d_res_type;
        private String d_res_value;
        private String d_res_clname;
        private String d_res_description;
        private String d_res_etime;
        private String d_res_pkid;
        private String d_res_rootid;
        private String update_time;
        private String update_user;
        private String d_res_parentid;
        private String create_user;
        private String d_res_stime;
        private Integer res_level;
        private String status;
        /**
         * bizKey : user_pkid
         * dataSourceId : 472848556017688624657
         * paramDataSource : [{"user_pkid":"526686187454791906471","login_name":"zxy"},{"user_pkid":"837999017930454056669","login_name":"zongwei.gao"},{"user_pkid":"575902978692665221835","login_name":"zihao"},{"user_pkid":"630465943861418428841","login_name":"zhangsan"},{"user_pkid":"19336654878130151852","login_name":"yuting.fu"},{"user_pkid":"795266446986237042622","login_name":"yongda"},{"user_pkid":"456762912664870127681","login_name":"xing"},{"user_pkid":"427567946717807224595","login_name":"xili"},{"user_pkid":"586090285331358101394","login_name":"windy3"},{"user_pkid":"586087026453670221939","login_name":"windy2"},{"user_pkid":"586083918186126148254","login_name":"windy1"},{"user_pkid":"456463890261305951361","login_name":"windy"},{"user_pkid":"697310799064298189203","login_name":"wangyi"},{"user_pkid":"689718131763748954293","login_name":"wangwu"},{"user_pkid":"61933288446670050516","login_name":"test"},{"user_pkid":"846555729688545128145","login_name":"sts"},{"user_pkid":"586096465079471504921","login_name":"shuting2"},{"user_pkid":"586093764448096883045","login_name":"shuting1"},{"user_pkid":"567920169958546222614","login_name":"shuting"},{"user_pkid":"145778043837201593623","login_name":"sfff"},{"user_pkid":"454980024013070447909","login_name":"sf"},{"user_pkid":"127404224735916336761","login_name":"QY"},{"user_pkid":"837781781245717540182","login_name":"Po"},{"user_pkid":"679844215169545750817","login_name":"partner"},{"user_pkid":"19342928494307298243","login_name":"nikita.li"},{"user_pkid":"664147562993352175722","login_name":"newuser1"},{"user_pkid":"602369547829900997405","login_name":"newuser"},{"user_pkid":"595672902469242597994","login_name":"maolin.long"},{"user_pkid":"19367607564303273821","login_name":"maggie.guo"},{"user_pkid":"532716465830788751834","login_name":"lml"},{"user_pkid":"636881597765407536797","login_name":"lisi"},{"user_pkid":"794033889160529671989","login_name":"LingZi"},{"user_pkid":"777061352964105477825","login_name":"lin"},{"user_pkid":"366430058210423826931","login_name":"libo"},{"user_pkid":"795264847963952429350","login_name":"laura.fu"},{"user_pkid":"19363098230099924519","login_name":"kingsley.wang"},{"user_pkid":"299565463901813483459","login_name":"jili"},{"user_pkid":"826603697661749904133","login_name":"hhh"},{"user_pkid":"689726736408579698812","login_name":"hh"},{"user_pkid":"19355432096497914372","login_name":"hao.wang"},{"user_pkid":"26883263308468127524","login_name":"glt"},{"user_pkid":"116699045447239841472","login_name":"fzg"},{"user_pkid":"568464141045360661396","login_name":"freya.li"},{"user_pkid":"196275601331049874555","login_name":"freya"},{"user_pkid":"24174329360877589197","login_name":"fengjunjian"},{"user_pkid":"795263125168776659820","login_name":"elsa.wang"},{"user_pkid":"844376757620323649271","login_name":"datacvg_huabu"},{"user_pkid":"664234403985387821141","login_name":"datacvg"},{"user_pkid":"298164987386432024627","login_name":"Danny"},{"user_pkid":"828753766922449729724","login_name":"citianbao"},{"user_pkid":"828750116998739057683","login_name":"cishenpi"},{"user_pkid":"715864220673191848566","login_name":"cici"},{"user_pkid":"26497012505134981410","login_name":"chris"},{"user_pkid":"19351343445550813262","login_name":"chenming.chen"},{"user_pkid":"776661974011373542188","login_name":"chenbing"},{"user_pkid":"783893290417090501894","login_name":"cfs1"},{"user_pkid":"374023680702637182462","login_name":"cfs"},{"user_pkid":"93484176923910964144","login_name":"ceshi"},{"user_pkid":"838325071445199522980","login_name":"CDO"},{"user_pkid":"819733026018882818302","login_name":"bing.chen"},{"user_pkid":"795254863927443236564","login_name":"barry.chen"},{"user_pkid":"855674956040808673608","login_name":"admin1"},{"user_pkid":"10000000","login_name":"admin"},{"user_pkid":"332772508658252637544","login_name":"574"}]
         * paramDefaultVal :
         * paramLevel : 1
         * selectSql : SELECT
         user_pkid,login_name
         FROM
         bi_sys_userinfo ORDER BY login_name DESC
         * setId : 472853544532276162571
         * setIndex : id01
         * setName : 一级-username
         */

        private String bizKey;
        private String dataSourceId;
        private String paramDataSource;
        private String paramDefaultVal;
        private String paramLevel;
        private String selectSql;
        private String setId;
        private String setIndex;
        private String setName;



        public String getBizKey() {
            return bizKey;
        }

        public void setBizKey(String bizKey) {
            this.bizKey = bizKey;
        }

        public String getDataSourceId() {
            return dataSourceId;
        }

        public void setDataSourceId(String dataSourceId) {
            this.dataSourceId = dataSourceId;
        }

        public String getParamDataSource() {
            return paramDataSource;
        }

        public void setParamDataSource(String paramDataSource) {
            this.paramDataSource = paramDataSource;
        }

        public String getParamDefaultVal() {
            return paramDefaultVal;
        }

        public void setParamDefaultVal(String paramDefaultVal) {
            this.paramDefaultVal = paramDefaultVal;
        }

        public String getParamLevel() {
            return paramLevel;
        }

        public void setParamLevel(String paramLevel) {
            this.paramLevel = paramLevel;
        }

        public String getSelectSql() {
            return selectSql;
        }

        public void setSelectSql(String selectSql) {
            this.selectSql = selectSql;
        }

        public String getSetId() {
            return setId;
        }

        public void setSetId(String setId) {
            this.setId = setId;
        }

        public String getSetIndex() {
            return setIndex;
        }

        public void setSetIndex(String setIndex) {
            this.setIndex = setIndex;
        }

        public String getSetName() {
            return setName;
        }

        public void setSetName(String setName) {
            this.setName = setName;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRelativeEnd() {
            return relativeEnd;
        }

        public void setRelativeEnd(String relativeEnd) {
            this.relativeEnd = relativeEnd;
        }

        public String getRelativeStart() {
            return relativeStart;
        }

        public void setRelativeStart(String relativeStart) {
            this.relativeStart = relativeStart;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public Integer getRes_level_sort() {
            return res_level_sort;
        }

        public void setRes_level_sort(Integer res_level_sort) {
            this.res_level_sort = res_level_sort;
        }

        public Boolean getExpires() {
            return expires;
        }

        public void setExpires(Boolean expires) {
            this.expires = expires;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getD_res_showtype() {
            return d_res_showtype;
        }

        public void setD_res_showtype(String d_res_showtype) {
            this.d_res_showtype = d_res_showtype;
        }

        public String getD_res_id() {
            return d_res_id;
        }

        public void setD_res_id(String d_res_id) {
            this.d_res_id = d_res_id;
        }

        public String getD_res_flname() {
            return d_res_flname;
        }

        public void setD_res_flname(String d_res_flname) {
            this.d_res_flname = d_res_flname;
        }

        public String getD_res_abb() {
            return d_res_abb;
        }

        public void setD_res_abb(String d_res_abb) {
            this.d_res_abb = d_res_abb;
        }

        public String getD_res_type() {
            return d_res_type;
        }

        public void setD_res_type(String d_res_type) {
            this.d_res_type = d_res_type;
        }

        public String getD_res_value() {
            return d_res_value;
        }

        public void setD_res_value(String d_res_value) {
            this.d_res_value = d_res_value;
        }

        public String getD_res_clname() {
            return d_res_clname;
        }

        public void setD_res_clname(String d_res_clname) {
            this.d_res_clname = d_res_clname;
        }

        public String getD_res_description() {
            return d_res_description;
        }

        public void setD_res_description(String d_res_description) {
            this.d_res_description = d_res_description;
        }

        public String getD_res_etime() {
            return d_res_etime;
        }

        public void setD_res_etime(String d_res_etime) {
            this.d_res_etime = d_res_etime;
        }

        public String getD_res_pkid() {
            return d_res_pkid;
        }

        public void setD_res_pkid(String d_res_pkid) {
            this.d_res_pkid = d_res_pkid;
        }

        public String getD_res_rootid() {
            return d_res_rootid;
        }

        public void setD_res_rootid(String d_res_rootid) {
            this.d_res_rootid = d_res_rootid;
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

        public String getD_res_parentid() {
            return d_res_parentid;
        }

        public void setD_res_parentid(String d_res_parentid) {
            this.d_res_parentid = d_res_parentid;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getD_res_stime() {
            return d_res_stime;
        }

        public void setD_res_stime(String d_res_stime) {
            this.d_res_stime = d_res_stime;
        }

        public Integer getRes_level() {
            return res_level;
        }

        public void setRes_level(Integer res_level) {
            this.res_level = res_level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public String getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
        }
    }
}
