package com.datacvg.dimp.bean;

import androidx.annotation.Keep;
/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-07
 * @Description :
 */
@Keep
public class ChartTypeBean {

    private String chartName ;
    private String chartType ;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public enum TypeEnviron {
        CHART_TEXT("text"),
        CHART_LONG("long_text"),
        CHART_LINE("line_chart"),
        CHART_BAR("bar_chart"),
        CHART_PIE("pie_chart"),
        CHART_DASHBOARD("dashboard"),
        CHART_BULLET("bullet_map");

        String chartType ;

        private TypeEnviron(String value) {
            this.chartType = value;
        }

        public String getValue() {
            return this.chartType;
        }

        public static ChartTypeBean.TypeEnviron of(String intValue) {

            switch (intValue) {
                case "long_text":
                    return CHART_LONG;

                case "line_chart":
                    return CHART_LINE;

                case "bar_chart":
                    return CHART_BAR;

                case "pie_chart":
                    return CHART_PIE;

                case "dashboard":
                    return CHART_DASHBOARD;

                case "bullet_map":
                    return CHART_BULLET;

                default:
                    return CHART_TEXT;
            }
        }
    }

    public void setEnviroment(String env) {
        switch (ChartTypeBean.TypeEnviron.of(env)) {
            case CHART_LONG:
                chartType = "long_text" ;
                chartName = "长文本" ;
                break;

            case CHART_LINE:
                chartType = "line_chart" ;
                chartName = "折线图" ;
                break;

            case CHART_BAR:
                chartType = "bar_chart" ;
                chartName = "柱状图" ;
                break;

            case CHART_PIE:
                chartType = "pie_chart" ;
                chartName = "饼图" ;
                break;

            case CHART_DASHBOARD:
                chartType = "dashboard" ;
                chartName = "仪表盘" ;
                break;

            case CHART_BULLET:
                chartType = "bullet_map" ;
                chartName = "子弹图" ;
                break;

            default:
                chartType = "text" ;
                chartName = "短文本" ;
                break;

        }
    }
}
