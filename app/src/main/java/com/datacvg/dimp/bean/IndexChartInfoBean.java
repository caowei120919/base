package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-12
 * @Description :
 */
@Keep
public class IndexChartInfoBean implements Serializable {

    private List<IndexChartDetailBean> indexChart;

    public List<IndexChartDetailBean> getIndexChart() {
        return indexChart;
    }

    public void setIndexChart(List<IndexChartDetailBean> indexChart) {
        this.indexChart = indexChart;
    }


    @Keep
    public static class IndexChartDetailBean implements Serializable {
        /**
         * name : 财务成果
         * detail : [{"index_id":"IBI-mtn_inc","index_clname":"运维收入","index_flname":null,"chart_type":"text","chart_top_title":"","chart_bottom_title":"","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"1","chart_high":"1","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"15341079964696855071","analysis_dimension":"TIME","index_description":"哈哈哈哈哈哈哈哈哈","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"minth_01_004","index_clname":"净利润","index_flname":null,"chart_type":"text","chart_top_title":"","chart_bottom_title":"目标","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"个","chart_wide":"1","chart_high":"1","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"146789384078228353","analysis_dimension":"TIME","index_description":"净利润=收入-成本-税金+其他业务收入-其他业务支出-销售费用-管理费用-财务费用+营业外收入-营业外支出+投资收益-所得税","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"shulre_MONTH","index_clname":"月利润额","index_flname":null,"chart_type":"text","chart_top_title":"","chart_bottom_title":"","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"%","chart_wide":"1","chart_high":"1","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"1763989426923164057372","analysis_dimension":"TIME","index_description":"","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"IBI-ex-cost_MONTH","index_clname":"事业部成本","index_flname":null,"chart_type":"bar_chart","chart_top_title":"","chart_bottom_title":"\t\t\t\t\t\t\t\t\t环比","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"1829296769592209620721","analysis_dimension":"TIME","index_description":"【指标说明】费用分摊前事业部成本。【计算逻辑】薪资+报销+借调支出+房租+外包支出+现金补贴+借调工时调整+借调工时折扣","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"IBI-inc_dvs","index_clname":"事业部收入","index_flname":null,"chart_type":"line_chart","chart_top_title":"","chart_bottom_title":"同期","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"15171776569319093610","analysis_dimension":"TIME","index_description":"指标说明：事业部收入\r\n计算逻辑：项目收入+产品销售毛利+售前收入+运维收入+借调收入\r\n","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"IBI-cost","index_clname":"事业部成本（含分摊费用）","index_flname":null,"chart_type":"dashboard","chart_top_title":"","chart_bottom_title":"\t\t\t\t\t\t\t\t\t","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"15175287335273652255","analysis_dimension":"TIME","index_description":"","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"IBI-profit","index_clname":"分摊后利润","index_flname":null,"chart_type":"text","chart_top_title":"","chart_bottom_title":"","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"1","chart_high":"1","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"15355223458087208533","analysis_dimension":"TIME","index_description":"","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"IBI-income","index_clname":"利润","index_flname":null,"chart_type":"bullet_map","chart_top_title":"","chart_bottom_title":"\t\t\t\t\t\t\t同期","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"77430763895017608302","analysis_dimension":"TIME","index_description":"这是利润","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"profit001","index_clname":"净利润pro","index_flname":null,"chart_type":"line_chart","chart_top_title":"","chart_bottom_title":"实际收益","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"元","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"257961335869653659636","analysis_dimension":"TIME","index_description":"净利润","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"xssrzzl","index_clname":"月销售收入增长率","index_flname":null,"chart_type":"bar_chart","chart_top_title":"","chart_bottom_title":"上期","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"%","chart_wide":"2","chart_high":"2","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"181310680701982851283","analysis_dimension":"TIME","index_description":"","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null},{"index_id":"shuincome_MONTH","index_clname":"月主营业务收入","index_flname":null,"chart_type":"long_text","chart_top_title":"","chart_bottom_title":"\t\t\t\t\t\t\t\t\t计划","chart_default_valtype":null,"chart_bottom_valtype":null,"chart_unit":"%","chart_wide":"2","chart_high":"1","chart_end_time":"0","chart_range":null,"threshold_flag":null,"index_pkid":"1764002464830360058951","analysis_dimension":"TIME","index_description":"","tflag":null,"index_type":"1108110367092326288374","index_threshold_type":null,"index_threshold_value":null,"index_classification_name":"财务成果","index_data":null,"index_default_color":null,"page_chart_type":null}]
         */

        private String name;
        private List<IndexChartBean> detail;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<IndexChartBean> getDetail() {
            return detail;
        }

        public void setDetail(List<IndexChartBean> detail) {
            this.detail = detail;
        }
    }
}
