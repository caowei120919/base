package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-11
 * @Description :
 */
@Keep
public class IndexTreeBean implements Serializable {
    /**
     * res_level_sort : 275
     * i_res_parentid : 0
     * index_threshold_type : 2
     * flag : T
     * value_mnt : 10000
     * thresholdChallenge :
     * vals : [{"index_data":"241.43","value_unit":"万元","value_description":"实际"}]
     * mnt_description : 万
     * index_default_color : #fead3d
     * i_res_rootid : 1829464754612507622586
     * index_description : 【指标说明】费用分摊前事业部成本。【计算逻辑】薪资+报销+借调支出+房租+外包支出+现金补贴+借调工时调整+借调工时折扣
     * orgname : 数聚股份
     * index_value_initial : 2414324.1268
     * value_unit : 万元
     * thresholdTarget: 225
     * index_id : IBI-ex-cost_MONTH
     * index_flname : Cost (Before allocation)
     * thresholdMinimum : 245
     * chart_default_valtype : ddb1
     * index_name : 事业部成本
     * value_fmt : ###,###.##
     * res_level : 0
     * rms_count :
     * index_clname : 事业部成本
     * index_pkid : 1829296769592209620721
     * pname : 产品或服务
     * i_res_id : 1829464754612507622586
     * funame : 所有地区
     * chart_unit : 元
     * nodes : [{"res_level_sort":0,"i_res_parentid":"1829464754612507622586","index_threshold_type":"2","flag":"T","value_mnt":"10000","thresholdChallenge":"180","vals":[{"index_data":"191.21","value_unit":"万元","value_description":"实际"}],"mnt_description":"万","index_default_color":"#fead3d","i_res_rootid":"1829464754612507622586","index_description":"","orgname":"数聚股份","index_value_initial":1912058.08,"value_unit":"万元","thresholdTarget":"190","index_id":"IBI-prl","index_flname":"Monthly Staffing cost","thresholdMinimum":"210","chart_default_valtype":"ddb1","index_name":"人力成本","value_fmt":"###,###.##","res_level":1,"rms_count":"","index_clname":"人力成本","index_pkid":"15452772621954916602","pname":"产品或服务","i_res_id":"1829500379994433124005","funame":"所有地区","chart_unit":"元","name":"人力成本","index_threshold_value":""},{"res_level_sort":1,"i_res_parentid":"1829464754612507622586","index_threshold_type":"2","flag":"T","value_mnt":"10000","thresholdChallenge":"","vals":[{"index_data":"6.43","value_unit":"万元","value_description":"实际"}],"mnt_description":"万","index_default_color":"#51CC00","i_res_rootid":"1829464754612507622586","index_description":"","orgname":"数聚股份","index_value_initial":64290.14,"value_unit":"万元","thresholdTarget":"18.58","index_id":"IBI-exp_MONTH","index_flname":"Monthly reimbursement","thresholdMinimum":"","chart_default_valtype":"ddb1","index_name":"费用报销","value_fmt":"###,###.##","res_level":1,"rms_count":"","index_clname":"费用报销","index_pkid":"1830944049545852083526","pname":"产品或服务","i_res_id":"1829500380336748530979","funame":"所有地区","chart_unit":"元","name":"费用报销","index_threshold_value":""},{"res_level_sort":3,"i_res_parentid":"1829464754612507622586","index_threshold_type":"2","flag":"T","value_mnt":"10000","thresholdChallenge":"","vals":[{"index_data":"2.19","value_unit":"万元","value_description":"实际"},{"index_data":"0.55","value_unit":"万元","value_description":"上期"}],"mnt_description":"万","index_default_color":"#fead3d","i_res_rootid":"1829464754612507622586","index_description":"","orgname":"数聚股份","index_value_initial":21881.2584,"value_unit":"万元","thresholdTarget":"2","index_id":"IBI-outs_exp","index_flname":"Outsourcing cost","thresholdMinimum":"3","chart_default_valtype":"ddb1","index_name":"外包支出","value_fmt":"###,###.##","res_level":1,"rms_count":"","index_clname":"外包支出","index_pkid":"15904801197582941653","pname":"产品或服务","i_res_id":"1829500381319905971987","funame":"所有地区","chart_unit":"元","name":"外包支出","index_threshold_value":""},{"res_level_sort":4,"i_res_parentid":"1829464754612507622586","index_threshold_type":"2","flag":"T","value_mnt":"10000","thresholdChallenge":"","vals":[{"index_data":"0","value_unit":"万元","value_description":"实际"}],"mnt_description":"万","index_default_color":"#fead3d","i_res_rootid":"1829464754612507622586","index_description":"","orgname":"数聚股份","index_value_initial":0,"value_unit":"万元","thresholdTarget":"","index_id":"IBI-int_exp","index_flname":"Staffing cost","thresholdMinimum":"0","chart_default_valtype":"ddb1","index_name":"借调支出","value_fmt":"###,###.##","res_level":1,"rms_count":"","index_clname":"借调支出","index_pkid":"15886674710611274368","pname":"产品或服务","i_res_id":"1829500380662023709665","funame":"所有地区","chart_unit":"元","name":"借调支出","index_threshold_value":""},{"res_level_sort":5,"i_res_parentid":"1829464754612507622586","index_threshold_type":"2","flag":"T","value_mnt":"10000","thresholdChallenge":"","vals":[{"index_data":"1.61","value_unit":"万元","value_description":"实际"}],"mnt_description":"万","index_default_color":"#fead3d","i_res_rootid":"1829464754612507622586","index_description":"","orgname":"数聚股份","index_value_initial":16094.6484,"value_unit":"万元","thresholdTarget":"1.5","index_id":"IBI-rent","index_flname":"Rental","thresholdMinimum":"2","chart_default_valtype":"ddb1","index_name":"房租","value_fmt":"###,###.##","res_level":1,"rms_count":"","index_clname":"房租","index_pkid":"15888980789633591066","pname":"产品或服务","i_res_id":"1829500380988886312485","funame":"所有地区","chart_unit":"元","name":"房租","index_threshold_value":""}]
     * name : 事业部成本
     * index_threshold_value :
     */

    private Integer res_level_sort;
    private String i_res_parentid;
    private String index_threshold_type;
    private String flag;
    private String value_mnt;
    private String thresholdChallenge;
    private String mnt_description;
    private String index_default_color;
    private String i_res_rootid;
    private String index_description;
    private String orgname;
    private Double index_value_initial;
    private String value_unit;
    private String thresholdTarget;
    private String index_id;
    private String index_flname;
    private String thresholdMinimum;
    private String chart_default_valtype;
    private String index_name;
    private String value_fmt;
    private Integer res_level;
    private Integer viewPosition = 0;
    private String rms_count;

    public Integer getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(Integer viewPosition) {
        this.viewPosition = viewPosition;
    }

    private String index_clname;
    private String index_pkid;
    private String pname;
    private String i_res_id;
    private String funame;
    private String chart_unit;
    private String name;
    private String index_threshold_value;
    private List<ValsBean> vals;
    private List<IndexTreeBean> nodes;

    @Keep
    public static class ValsBean implements Serializable{
        /**
         * index_data : 241.43
         * value_unit : 万元
         * value_description : 实际
         */

        private String index_data;
        private String value_unit;
        private String value_description;

        public String getIndex_data() {
            return index_data;
        }

        public void setIndex_data(String index_data) {
            this.index_data = index_data;
        }

        public String getValue_unit() {
            return value_unit;
        }

        public void setValue_unit(String value_unit) {
            this.value_unit = value_unit;
        }

        public String getValue_description() {
            return value_description;
        }

        public void setValue_description(String value_description) {
            this.value_description = value_description;
        }
    }

    public Integer getRes_level_sort() {
        return res_level_sort;
    }

    public void setRes_level_sort(Integer res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getI_res_parentid() {
        return i_res_parentid;
    }

    public void setI_res_parentid(String i_res_parentid) {
        this.i_res_parentid = i_res_parentid;
    }

    public String getIndex_threshold_type() {
        return index_threshold_type;
    }

    public void setIndex_threshold_type(String index_threshold_type) {
        this.index_threshold_type = index_threshold_type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getValue_mnt() {
        return value_mnt;
    }

    public void setValue_mnt(String value_mnt) {
        this.value_mnt = value_mnt;
    }

    public String getThresholdChallenge() {
        return thresholdChallenge;
    }

    public void setThresholdChallenge(String thresholdChallenge) {
        this.thresholdChallenge = thresholdChallenge;
    }

    public String getMnt_description() {
        return mnt_description;
    }

    public void setMnt_description(String mnt_description) {
        this.mnt_description = mnt_description;
    }

    public String getIndex_default_color() {
        return index_default_color;
    }

    public void setIndex_default_color(String index_default_color) {
        this.index_default_color = index_default_color;
    }

    public String getI_res_rootid() {
        return i_res_rootid;
    }

    public void setI_res_rootid(String i_res_rootid) {
        this.i_res_rootid = i_res_rootid;
    }

    public String getIndex_description() {
        return index_description;
    }

    public void setIndex_description(String index_description) {
        this.index_description = index_description;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Double getIndex_value_initial() {
        return index_value_initial;
    }

    public void setIndex_value_initial(Double index_value_initial) {
        this.index_value_initial = index_value_initial;
    }

    public String getValue_unit() {
        return value_unit;
    }

    public void setValue_unit(String value_unit) {
        this.value_unit = value_unit;
    }

    public String getThresholdTarget() {
        return thresholdTarget;
    }

    public void setThresholdTarget(String thresholdTarget) {
        this.thresholdTarget = thresholdTarget;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public String getIndex_flname() {
        return index_flname;
    }

    public void setIndex_flname(String index_flname) {
        this.index_flname = index_flname;
    }

    public String getThresholdMinimum() {
        return thresholdMinimum;
    }

    public void setThresholdMinimum(String thresholdMinimum) {
        this.thresholdMinimum = thresholdMinimum;
    }

    public String getChart_default_valtype() {
        return chart_default_valtype;
    }

    public void setChart_default_valtype(String chart_default_valtype) {
        this.chart_default_valtype = chart_default_valtype;
    }

    public String getIndex_name() {
        return index_name;
    }

    public void setIndex_name(String index_name) {
        this.index_name = index_name;
    }

    public String getValue_fmt() {
        return value_fmt;
    }

    public void setValue_fmt(String value_fmt) {
        this.value_fmt = value_fmt;
    }

    public Integer getRes_level() {
        return res_level;
    }

    public void setRes_level(Integer res_level) {
        this.res_level = res_level;
    }

    public String getRms_count() {
        return rms_count;
    }

    public void setRms_count(String rms_count) {
        this.rms_count = rms_count;
    }

    public String getIndex_clname() {
        return index_clname;
    }

    public void setIndex_clname(String index_clname) {
        this.index_clname = index_clname;
    }

    public String getIndex_pkid() {
        return index_pkid;
    }

    public void setIndex_pkid(String index_pkid) {
        this.index_pkid = index_pkid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getI_res_id() {
        return i_res_id;
    }

    public void setI_res_id(String i_res_id) {
        this.i_res_id = i_res_id;
    }

    public String getFuname() {
        return funame;
    }

    public void setFuname(String funame) {
        this.funame = funame;
    }

    public String getChart_unit() {
        return chart_unit;
    }

    public void setChart_unit(String chart_unit) {
        this.chart_unit = chart_unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex_threshold_value() {
        return index_threshold_value;
    }

    public void setIndex_threshold_value(String index_threshold_value) {
        this.index_threshold_value = index_threshold_value;
    }

    public List<ValsBean> getVals() {
        return vals;
    }

    public void setVals(List<ValsBean> vals) {
        this.vals = vals;
    }

    public List<IndexTreeBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<IndexTreeBean> nodes) {
        this.nodes = nodes;
    }
}
