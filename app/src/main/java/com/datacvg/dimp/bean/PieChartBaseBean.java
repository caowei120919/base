package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-15
 * @Description :
 */
@Keep
public class PieChartBaseBean {

    /**
     * backgroundColor : #22C5A6
     * series : [{"center":["50%","55%"],"data":[{"name":"202009","value":"208"},{"name":"202010","value":"294"},{"name":"202011","value":"204"},{"name":"202012","value":"193"},{"name":"202101","value":"203"},{"name":"202102","value":"222"}],"radius":"60%","type":"pie"}]
     * title : {"text":"在职员工数","textStyle":{"fontFamily":"微软雅黑, Arial, Verdana, sans-serif","fontSize":22}}
     * tooltip : {"formatter":"{b} : {c} 人 ({d} %)","trigger":"item"}
     */

    private String backgroundColor;
    private List<SeriesBean> series;
    private TitleBean title;
    private TooltipBean tooltip;
    private String[] colorsBean ;

    public String[] getColorsBean() {
        return new String[]{"#FA5A5A","#13AE90","#3C3D77","#FEAD3D","#205E68",
                "#F89F92","#F5D769","#A8C059","#F1A347","#7DCAE1"};
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<SeriesBean> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesBean> series) {
        this.series = series;
    }

    public TitleBean getTitle() {
        return title;
    }

    public void setTitle(TitleBean title) {
        this.title = title;
    }

    public TooltipBean getTooltip() {
        return tooltip;
    }

    public void setTooltip(TooltipBean tooltip) {
        this.tooltip = tooltip;
    }

    @Keep
    public static class TitleBean {
        /**
         * text : 在职员工数
         * textStyle : {"fontFamily":"微软雅黑, Arial, Verdana, sans-serif","fontSize":22}
         */

        private String text;
        private TextStyleBean textStyle;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public TextStyleBean getTextStyle() {
            return textStyle;
        }

        public void setTextStyle(TextStyleBean textStyle) {
            this.textStyle = textStyle;
        }

        @Keep
        public static class TextStyleBean {
            /**
             * fontFamily : 微软雅黑, Arial, Verdana, sans-serif
             * fontSize : 22
             */

            private String fontFamily;
            private Integer fontSize;

            public String getFontFamily() {
                return fontFamily;
            }

            public void setFontFamily(String fontFamily) {
                this.fontFamily = fontFamily;
            }

            public Integer getFontSize() {
                return fontSize;
            }

            public void setFontSize(Integer fontSize) {
                this.fontSize = fontSize;
            }
        }
    }

    @Keep
    public static class TooltipBean {
        /**
         * formatter : {b} : {c} 人 ({d} %)
         * trigger : item
         */

        private String formatter;
        private String trigger;

        public String getFormatter() {
            return formatter;
        }

        public void setFormatter(String formatter) {
            this.formatter = formatter;
        }

        public String getTrigger() {
            return trigger;
        }

        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }
    }

    @Keep
    public static class SeriesBean {
        /**
         * center : ["50%","55%"]
         * data : [{"name":"202009","value":"208"},{"name":"202010","value":"294"},{"name":"202011","value":"204"},{"name":"202012","value":"193"},{"name":"202101","value":"203"},{"name":"202102","value":"222"}]
         * radius : 60%
         * type : pie
         */

        private List<String> center;
        private List<DataBean> data;
        private String radius;
        private String type;

        public List<String> getCenter() {
            return center;
        }

        public void setCenter(List<String> center) {
            this.center = center;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Keep
        public static class DataBean {
            /**
             * name : 202009
             * value : 208
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
