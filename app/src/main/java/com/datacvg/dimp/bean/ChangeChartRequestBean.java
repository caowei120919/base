package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :
 */
@Keep
public class ChangeChartRequestBean {

    /**
     * bisysindexposition : [{"page":"8","index_id":"hsf-offline-sales","size_x":"2","size_y":"2","pos_x":"5","pos_y":"1"},{"page":"8","index_id":"hsf-mli","size_x":"2","size_y":"2","pos_x":"1","pos_y":"1"},{"page":"8","index_id":"hsf-revenue","size_x":"2","size_y":"1","pos_x":"1","pos_y":"3"}]
     * pad_number : 8
     * pad_name : 测试双维度，勿动
     */

    private List<BisysindexpositionBean> bisysindexposition;
    private String pad_number;
    private String pad_name;

    public List<BisysindexpositionBean> getBisysindexposition() {
        return bisysindexposition;
    }

    public void setBisysindexposition(List<BisysindexpositionBean> bisysindexposition) {
        this.bisysindexposition = bisysindexposition;
    }

    public String getPad_number() {
        return pad_number;
    }

    public void setPad_number(String pad_number) {
        this.pad_number = pad_number;
    }

    public String getPad_name() {
        return pad_name;
    }

    public void setPad_name(String pad_name) {
        this.pad_name = pad_name;
    }

    public static class BisysindexpositionBean {
        /**
         * page : 8
         * index_id : hsf-offline-sales
         * size_x : 2
         * size_y : 2
         * pos_x : 5
         * pos_y : 1
         */

        private String page;
        private String index_id;
        private String size_x;
        private String size_y;
        private String pos_x;
        private String pos_y;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
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

        public String getPos_x() {
            return pos_x;
        }

        public void setPos_x(String pos_x) {
            this.pos_x = pos_x;
        }

        public String getPos_y() {
            return pos_y;
        }

        public void setPos_y(String pos_y) {
            this.pos_y = pos_y;
        }
    }
}
