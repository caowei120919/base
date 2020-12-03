package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 图表数据实体
 */
@Keep
public class ChartBean {

    /**
     * chart_range : 5
     * index_threshold_type : 1
     * value_mnt : 0.01
     * thresholdChallenge :
     * mnt_description : 百分比
     * chart_high : 1
     * index_default_color : #FF3300
     * financial_index_flag :
     * chart_bottom_title :
     * index_description : 指标说明：当月参与售前案件的成功率
     计算逻辑：成功签约案件数/总接触案件数
     * thresholdUnit : %
     * chart_type : long_text
     * value_unit : %
     * thresholdTarget :
     * index_id : IBI-suc_r_pres
     * index_flname : Pre-sales success rate
     * thresholdMinimum : 50
     * chart_default_valtype : ddb1
     * value_fmt : ######.##
     * chart_end_time : 0
     * index_clname : 售前成功率
     * chart_wide : 2
     * index_pkid : 15874674043139381085
     * chart_bottom_valtype :
     * chart_unit : %
     * default_value : 24.65
     * chart_top_title :
     * bgcolor : #FF3300
     * index_data : 0.2465
     * name : 售前成功率
     * bottom_value :
     * index_threshold_value :
     */
    private int chart_range;
    private String index_threshold_type;
    private String value_mnt;
    private String thresholdChallenge;
    private String mnt_description;
    private int chart_high;
    private String index_default_color;
    private String financial_index_flag;
    private String chart_bottom_title;
    private String index_description;
    private String thresholdUnit;
    private String chart_type;
    private String value_unit;
    private String thresholdTarget;
    private String index_id;
    private String index_flname;
    private String thresholdMinimum;
    private String chart_default_valtype;
    private String value_fmt;
    private int chart_end_time;
    private String index_clname;
    private int chart_wide;
    private String index_pkid;
    private String chart_bottom_valtype;
    private String chart_unit;
    private String default_value;
    private String chart_top_title;
    private String bgcolor;
    private String index_data;
    private String name;
    private String bottom_value;
    private String index_threshold_value;
    private String chartColor;
    private String echarts_type;
    private String id;
    private String plan;
    private String actual;
    private List<ThresholdArrBean> thresholdArr;
    private List<String> colorArr;
    private OptionBean option;
    private String same_period;
    private String ring_ratio;
    private String year_on_year;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getSame_period() {
        return same_period;
    }

    public void setSame_period(String same_period) {
        this.same_period = same_period;
    }

    public String getRing_ratio() {
        return ring_ratio;
    }

    public void setRing_ratio(String ring_ratio) {
        this.ring_ratio = ring_ratio;
    }

    public String getYear_on_year() {
        return year_on_year;
    }

    public void setYear_on_year(String year_on_year) {
        this.year_on_year = year_on_year;
    }

    public List<ThresholdArrBean> getThresholdArr() {
        return thresholdArr;
    }

    public void setThresholdArr(List<ThresholdArrBean> thresholdArr) {
        this.thresholdArr = thresholdArr;
    }

    public List<String> getColorArr() {
        return colorArr;
    }

    public void setColorArr(List<String> colorArr) {
        this.colorArr = colorArr;
    }

    public int getChart_range() {
        return chart_range;
    }

    public void setChart_range(int chart_range) {
        this.chart_range = chart_range;
    }

    public String getIndex_threshold_type() {
        return index_threshold_type;
    }

    public void setIndex_threshold_type(String index_threshold_type) {
        this.index_threshold_type = index_threshold_type;
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

    public int getChart_high() {
        return chart_high;
    }

    public void setChart_high(int chart_high) {
        this.chart_high = chart_high;
    }

    public String getIndex_default_color() {
        return index_default_color;
    }

    public void setIndex_default_color(String index_default_color) {
        this.index_default_color = index_default_color;
    }

    public String getFinancial_index_flag() {
        return financial_index_flag;
    }

    public void setFinancial_index_flag(String financial_index_flag) {
        this.financial_index_flag = financial_index_flag;
    }

    public String getChart_bottom_title() {
        return chart_bottom_title;
    }

    public void setChart_bottom_title(String chart_bottom_title) {
        this.chart_bottom_title = chart_bottom_title;
    }

    public String getIndex_description() {
        return index_description;
    }

    public void setIndex_description(String index_description) {
        this.index_description = index_description;
    }

    public String getThresholdUnit() {
        return thresholdUnit;
    }

    public void setThresholdUnit(String thresholdUnit) {
        this.thresholdUnit = thresholdUnit;
    }

    public String getChart_type() {
        return chart_type;
    }

    public void setChart_type(String chart_type) {
        this.chart_type = chart_type;
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

    public String getValue_fmt() {
        return value_fmt;
    }

    public void setValue_fmt(String value_fmt) {
        this.value_fmt = value_fmt;
    }

    public int getChart_end_time() {
        return chart_end_time;
    }

    public void setChart_end_time(int chart_end_time) {
        this.chart_end_time = chart_end_time;
    }

    public String getIndex_clname() {
        return index_clname;
    }

    public void setIndex_clname(String index_clname) {
        this.index_clname = index_clname;
    }

    public int getChart_wide() {
        return chart_wide;
    }

    public void setChart_wide(int chart_wide) {
        this.chart_wide = chart_wide;
    }

    public String getIndex_pkid() {
        return index_pkid;
    }

    public void setIndex_pkid(String index_pkid) {
        this.index_pkid = index_pkid;
    }

    public String getChart_bottom_valtype() {
        return chart_bottom_valtype;
    }

    public void setChart_bottom_valtype(String chart_bottom_valtype) {
        this.chart_bottom_valtype = chart_bottom_valtype;
    }

    public String getChart_unit() {
        return chart_unit;
    }

    public void setChart_unit(String chart_unit) {
        this.chart_unit = chart_unit;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public String getChart_top_title() {
        return chart_top_title;
    }

    public void setChart_top_title(String chart_top_title) {
        this.chart_top_title = chart_top_title;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getIndex_data() {
        return index_data;
    }

    public void setIndex_data(String index_data) {
        this.index_data = index_data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBottom_value() {
        return bottom_value;
    }

    public String getChartColor() {
        return chartColor;
    }

    public void setChartColor(String chartColor) {
        this.chartColor = chartColor;
    }

    public String getEcharts_type() {
        return echarts_type;
    }

    public void setEcharts_type(String echarts_type) {
        this.echarts_type = echarts_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OptionBean getOption() {
        return option;
    }

    public void setOption(OptionBean option) {
        this.option = option;
    }

    public void setBottom_value(String bottom_value) {
        this.bottom_value = bottom_value;
    }

    public String getIndex_threshold_value() {
        return index_threshold_value;
    }

    public void setIndex_threshold_value(String index_threshold_value) {
        this.index_threshold_value = index_threshold_value;
    }

    public static class OptionBean {
        /**
         * backgroundColor : #22C5A6
         * calculable : false
         * title : {"text":"商品收入","textStyle":{"fontSize":22,"fontFamily":"微软雅黑, Arial, Verdana, sans-serif"}}
         * tooltip : {"trigger":"axis","formatter":"{b} : {c} 万元"}
         * xAxis : [{"type":"category","data":["201911","201912","202001","202002","202003","202004"]}]
         * yAxis : [{"scale":true,"type":"value","axisLine":{"show":true},"axisLabel":{"formatter":"{value} "}}]
         * series : [{"barCategoryGap":"50%","type":"bar","data":["522.6","574.86","306.11","302.46","304.21","303.91"]}]
         */

        private String backgroundColor;
        private boolean calculable;
        private OptionBean.TitleBean title;
        private OptionBean.TooltipBean tooltip;
        private List<OptionBean.XAxisBean> xAxis;
        private List<OptionBean.YAxisBean> yAxis;
        private List<OptionBean.SeriesBean> series;

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public boolean isCalculable() {
            return calculable;
        }

        public void setCalculable(boolean calculable) {
            this.calculable = calculable;
        }

        public OptionBean.TitleBean getTitle() {
            return title;
        }

        public void setTitle(OptionBean.TitleBean title) {
            this.title = title;
        }

        public OptionBean.TooltipBean getTooltip() {
            return tooltip;
        }

        public void setTooltip(OptionBean.TooltipBean tooltip) {
            this.tooltip = tooltip;
        }

        public List<OptionBean.XAxisBean> getXAxis() {
            return xAxis;
        }

        public void setXAxis(List<OptionBean.XAxisBean> xAxis) {
            this.xAxis = xAxis;
        }

        public List<OptionBean.YAxisBean> getYAxis() {
            return yAxis;
        }

        public void setYAxis(List<OptionBean.YAxisBean> yAxis) {
            this.yAxis = yAxis;
        }

        public List<OptionBean.SeriesBean> getSeries() {
            return series;
        }

        public void setSeries(List<OptionBean.SeriesBean> series) {
            this.series = series;
        }

        public static class TitleBean {
            /**
             * text : 商品收入
             * textStyle : {"fontSize":22,"fontFamily":"微软雅黑, Arial, Verdana, sans-serif"}
             */

            private String text;
            private OptionBean.TitleBean.TextStyleBean textStyle;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public OptionBean.TitleBean.TextStyleBean getTextStyle() {
                return textStyle;
            }

            public void setTextStyle(OptionBean.TitleBean.TextStyleBean textStyle) {
                this.textStyle = textStyle;
            }

            public static class TextStyleBean {
                /**
                 * fontSize : 22
                 * fontFamily : 微软雅黑, Arial, Verdana, sans-serif
                 */

                private int fontSize;
                private String fontFamily;

                public int getFontSize() {
                    return fontSize;
                }

                public void setFontSize(int fontSize) {
                    this.fontSize = fontSize;
                }

                public String getFontFamily() {
                    return fontFamily;
                }

                public void setFontFamily(String fontFamily) {
                    this.fontFamily = fontFamily;
                }
            }
        }

        public static class TooltipBean {
            /**
             * trigger : axis
             * formatter : {b} : {c} 万元
             */

            private String trigger;
            private String formatter;

            public String getTrigger() {
                return trigger;
            }

            public void setTrigger(String trigger) {
                this.trigger = trigger;
            }

            public String getFormatter() {
                return formatter;
            }

            public void setFormatter(String formatter) {
                this.formatter = formatter;
            }
        }

        public static class XAxisBean {
            /**
             * type : category
             * data : ["201911","201912","202001","202002","202003","202004"]
             */

            private String type;
            private List<String> data;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<String> getData() {
                return data;
            }

            public void setData(List<String> data) {
                this.data = data;
            }
        }

        public static class YAxisBean {
            /**
             * scale : true
             * type : value
             * axisLine : {"show":true}
             * axisLabel : {"formatter":"{value} "}
             */

            private boolean scale;
            private String type;
            private OptionBean.YAxisBean.AxisLineBean axisLine;
            private OptionBean.YAxisBean.AxisLabelBean axisLabel;

            public boolean isScale() {
                return scale;
            }

            public void setScale(boolean scale) {
                this.scale = scale;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public OptionBean.YAxisBean.AxisLineBean getAxisLine() {
                return axisLine;
            }

            public void setAxisLine(OptionBean.YAxisBean.AxisLineBean axisLine) {
                this.axisLine = axisLine;
            }

            public OptionBean.YAxisBean.AxisLabelBean getAxisLabel() {
                return axisLabel;
            }

            public void setAxisLabel(OptionBean.YAxisBean.AxisLabelBean axisLabel) {
                this.axisLabel = axisLabel;
            }

            public static class AxisLineBean {
                /**
                 * show : true
                 */

                private boolean show;

                public boolean isShow() {
                    return show;
                }

                public void setShow(boolean show) {
                    this.show = show;
                }
            }

            public static class AxisLabelBean {
                /**
                 * formatter : {value}
                 */

                private String formatter;

                public String getFormatter() {
                    return formatter;
                }

                public void setFormatter(String formatter) {
                    this.formatter = formatter;
                }
            }
        }

        public static class SeriesBean {
            /**
             * barCategoryGap : 50%
             * type : bar
             * data : ["522.6","574.86","306.11","302.46","304.21","303.91"]
             */
            private String radius;
            private String barCategoryGap;
            private String type;
            private String symbol ;
            private int symbolSize ;
            private List<String> center;
            private List<Object> data ;
            private List<OptionBean.SeriesBean.DataBean> dataBean;

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public int getSymbolSize() {
                return symbolSize;
            }

            public void setSymbolSize(int symbolSize) {
                this.symbolSize = symbolSize;
            }

            public String getBarCategoryGap() {
                return barCategoryGap;
            }

            public void setBarCategoryGap(String barCategoryGap) {
                this.barCategoryGap = barCategoryGap;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Object> getData() {
                return data;
            }

            public void setData(List<Object> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * value : 42
                 * name : 大数据事业部
                 */

                private String value;
                private String name;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }

    public class ThresholdArrBean {
        private String type ;
        private String threshold_value ;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThreshold_value() {
            return threshold_value;
        }

        public void setThreshold_value(String threshold_value) {
            this.threshold_value = threshold_value;
        }
    }
}
