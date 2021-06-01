package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-15
 * @Description :
 */
@Keep
public class BarChartBaseBean {

    /**
     * legend : {"data":["实际","同期"]}
     * series : [{"data":["246.48","259.48","236.95","238.64","233.43","246.15","245.72","293.82","226.71","217.09","227.95","73.72"],"name":"实际","type":"bar"},{"data":["192.89","182.68","384.73","216.97","239.05","217.6","","","","222.32","233.43","255.66"],"name":"同期","type":"bar"}]
     * xAxis : [{"data":["202003","202004","202005","202006","202007","202008","202009","202010","202011","202012","202101","202102"]}]
     * yAxis : [{"axisLine":{"show":false},"name":"万元","type":"value"}]
     */

    private LegendBean legend;
    private List<SeriesBean> series;
    private List<XAxisBean> xAxis;
    private List<YAxisBean> yAxis;
    private String[] colorArr ;

    public String[] getColorArr() {
        String[] colors = new String[]{};
        return colors;
    }

    public void setColorArr(String[] colorArr) {
        this.colorArr = colorArr;
    }

    public LegendBean getLegend() {
        return legend;
    }

    public void setLegend(LegendBean legend) {
        this.legend = legend;
    }

    public List<SeriesBean> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesBean> series) {
        this.series = series;
    }

    public List<XAxisBean> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<XAxisBean> xAxis) {
        this.xAxis = xAxis;
    }

    public List<YAxisBean> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<YAxisBean> yAxis) {
        this.yAxis = yAxis;
    }

    @Keep
    public static class LegendBean {
        private List<String> data;
    }

    @Keep
    public static class SeriesBean {
        /**
         * data : ["246.48","259.48","236.95","238.64","233.43","246.15","245.72","293.82","226.71","217.09","227.95","73.72"]
         * name : 实际
         * type : bar
         */

        private List<String> data;
        private String name;
        private String type;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Keep
    public static class XAxisBean {
        private List<String> data;

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    @Keep
    public static class YAxisBean {
        /**
         * axisLine : {"show":false}
         * name : 万元
         * type : value
         */

        private AxisLineBean axisLine;
        private String name;
        private String type;

        public AxisLineBean getAxisLine() {
            return axisLine;
        }

        public void setAxisLine(AxisLineBean axisLine) {
            this.axisLine = axisLine;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Keep
        public static class AxisLineBean {
            /**
             * show : false
             */

            private Boolean show;

            public Boolean getShow() {
                return show;
            }

            public void setShow(Boolean show) {
                this.show = show;
            }
        }
    }
}
