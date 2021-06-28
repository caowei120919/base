package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-11
 * @Description :
 */
@Keep
public class DimensionForTimeBean {

    /**
     * dimensionRelation : {"dimensionResult":{"118341583624371459776":[{"id":"118341583624371459776","d_res_name":"产品或服务","pid":"0","d_res_rootid":"118341583624371459776","d_res_value":"GOODS","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":0,"res_level_sort":37,"nodes":[{"id":"118343610455720651903","d_res_name":"实施与服务","pid":"118341583624371459776","d_res_rootid":"118341583624371459776","d_res_value":"type","defaultkey":"GOODS","d_res_stime":"20000121","d_res_etime":"21000101","res_level":1,"res_level_sort":0,"nodes":[{"id":"118346247375637939612","d_res_name":"项目实施","pid":"118343610455720651903","d_res_rootid":"118341583624371459776","d_res_value":"PJ","defaultkey":"GOODS","d_res_stime":"20000121","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null},{"id":"118347182596466596922","d_res_name":"开发服务","pid":"118343610455720651903","d_res_rootid":"118341583624371459776","d_res_value":"RD","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":1,"nodes":null},{"id":"118349476943837226672","d_res_name":"人天服务","pid":"118343610455720651903","d_res_rootid":"118341583624371459776","d_res_value":"RTFW","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":2,"nodes":null}]},{"id":"118344664594576633849","d_res_name":"产品","pid":"118341583624371459776","d_res_rootid":"118341583624371459776","d_res_value":"PD","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":1,"nodes":[{"id":"118357774918926316248","d_res_name":"DIMP","pid":"118344664594576633849","d_res_rootid":"118341583624371459776","d_res_value":"DIMP","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null},{"id":"118358779880902465540","d_res_name":"SEMF","pid":"118344664594576633849","d_res_rootid":"118341583624371459776","d_res_value":"SEMF","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":1,"nodes":null},{"id":"118360000002644700497","d_res_name":"智慧采集平台","pid":"118344664594576633849","d_res_rootid":"118341583624371459776","d_res_value":"CJ","defaultkey":"GOODS","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":2,"nodes":null}]}]}],"118306192070461277956":[{"id":"118306192070461277956","d_res_name":"所有地区","pid":"0","d_res_rootid":"118306192070461277956","d_res_value":"region","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"20991231","res_level":0,"res_level_sort":36,"nodes":[{"id":"118309393954877902702","d_res_name":"浙江","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-ZJ","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":0,"nodes":[{"id":"118311326211022128931","d_res_name":"杭州","pid":"118309393954877902702","d_res_rootid":"118306192070461277956","d_res_value":"R-ZJ-HZ","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null},{"id":"118320644292446002811","d_res_name":"德清","pid":"118309393954877902702","d_res_rootid":"118306192070461277956","d_res_value":"R-ZJ-DQ","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":1,"nodes":null}]},{"id":"118312459307994374752","d_res_name":"北京","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-BJ","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":1,"nodes":null},{"id":"118314060723332943657","d_res_name":"上海","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-SH","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":2,"nodes":null},{"id":"118315658858350677722","d_res_name":"江苏","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-JS","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":3,"nodes":null},{"id":"118316968712839107692","d_res_name":"河北","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-HB","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":4,"nodes":[{"id":"118322578139465203980","d_res_name":"唐山","pid":"118316968712839107692","d_res_rootid":"118306192070461277956","d_res_value":"R-HB-TS","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null}]},{"id":"118324703767955315603","d_res_name":"山东","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-SD","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":5,"nodes":[{"id":"118327055093685278714","d_res_name":"青岛","pid":"118324703767955315603","d_res_rootid":"118306192070461277956","d_res_value":"R-SD-QD","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null},{"id":"118329103870114347013","d_res_name":"济南","pid":"118324703767955315603","d_res_rootid":"118306192070461277956","d_res_value":"R-SD-JN","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":1,"nodes":null}]},{"id":"118331266780821389467","d_res_name":"湖南","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-HUN","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":6,"nodes":[{"id":"118333036376218432478","d_res_name":"长沙","pid":"118331266780821389467","d_res_rootid":"118306192070461277956","d_res_value":"R-HUN-CS","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null}]},{"id":"118335355792065768519","d_res_name":"海南","pid":"118306192070461277956","d_res_rootid":"118306192070461277956","d_res_value":"R-HAIN","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":7,"nodes":[{"id":"118337690139405603602","d_res_name":"海口","pid":"118335355792065768519","d_res_rootid":"118306192070461277956","d_res_value":"R-HAIN-HK","defaultkey":"region","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":0,"nodes":null}]}]}],"1107632222252461530504":[{"id":"1107632222252461530504","d_res_name":"baobiao","pid":"0","d_res_rootid":"1107632222252461530504","d_res_value":"sys_type","defaultkey":"t","d_res_stime":"20000101","d_res_etime":"21000101","res_level":0,"res_level_sort":39,"nodes":[{"id":"1107634774417418973159","d_res_name":"APP_SYS","pid":"1107632222252461530504","d_res_rootid":"1107632222252461530504","d_res_value":"APP_SYS","defaultkey":"t","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":0,"nodes":null},{"id":"1107636143709738961635","d_res_name":"MANAGER_SYS","pid":"1107632222252461530504","d_res_rootid":"1107632222252461530504","d_res_value":"MANAGER_SYS","defaultkey":"t","d_res_stime":"20000101","d_res_etime":"21000101","res_level":1,"res_level_sort":1,"nodes":null}]}],"965332601103451593621":[{"id":"14860367656855969470","d_res_name":"数聚股份","pid":"965332601103451593621","d_res_rootid":"965332601103451593621","d_res_value":"DATACVG","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":0,"res_level_sort":8,"nodes":[{"id":"24992167968453710046","d_res_name":"管理创新事业部","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-1","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":1,"nodes":null},{"id":"14883460379602917474","d_res_name":"大数据事业部","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-2","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":2,"nodes":null},{"id":"24988510953860096287","d_res_name":"战略发展部","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-3","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":3,"nodes":null},{"id":"66653088127666091280","d_res_name":"北京分公司(已过期)","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-BJ","defaultkey":"user_org","d_res_stime":"20360601","d_res_etime":"21000101","res_level":2,"res_level_sort":5,"nodes":null},{"id":"66655027409736395867","d_res_name":"浙江子公司","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-ZJ","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":7,"nodes":[{"id":"3128313019172355004239","d_res_name":"业财融合部","pid":"66655027409736395867","d_res_rootid":"965332601103451593621","d_res_value":"D-ZJ-YCRH","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":3,"res_level_sort":0,"nodes":null},{"id":"3128315812388677702446","d_res_name":"信息管理部","pid":"66655027409736395867","d_res_rootid":"965332601103451593621","d_res_value":"D-ZJ-XXGL","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":3,"res_level_sort":1,"nodes":null}]},{"id":"345375277066276786770","d_res_name":"青岛分公司","pid":"14860367656855969470","d_res_rootid":"965332601103451593621","d_res_value":"D-QD","defaultkey":"user_org","d_res_stime":"20000101","d_res_etime":"21000101","res_level":2,"res_level_sort":10,"nodes":null}]}]},"dimensionName":[{"d_res_name":"组织维度","d_res_rootid":"965332601103451593621","d_res_pkid":"096533260110381557724","d_res_id":"965332601103451593621","d_res_value":"user_org","sort":1},{"d_res_name":"baobiao","d_res_rootid":"1107632222252461530504","d_res_pkid":"1107632222253647141716","d_res_id":"1107632222252461530504","d_res_value":"sys_type","sort":2},{"d_res_name":"所有地区","d_res_rootid":"118306192070461277956","d_res_pkid":"118306192070815470132","d_res_id":"118306192070461277956","d_res_value":"region","sort":3},{"d_res_name":"产品或服务","d_res_rootid":"118341583624371459776","d_res_pkid":"211834158362469344835","d_res_id":"118341583624371459776","d_res_value":"GOODS","sort":4}]}
     */

    private DimensionRelationBean dimensionRelation;

    public DimensionRelationBean getDimensionRelation() {
        return dimensionRelation;
    }

    public void setDimensionRelation(DimensionRelationBean dimensionRelation) {
        this.dimensionRelation = dimensionRelation;
    }

    @Keep
    public static class DimensionRelationBean {

        private JsonObject dimensionResult;
        private List<DimensionNameBean> dimensionName;

        public JsonObject getDimensionResult() {
            return dimensionResult;
        }

        public void setDimensionResult(JsonObject dimensionResult) {
            this.dimensionResult = dimensionResult;
        }

        public List<DimensionNameBean> getDimensionName() {
            return dimensionName;
        }

        public void setDimensionName(List<DimensionNameBean> dimensionName) {
            this.dimensionName = dimensionName;
        }


        @Keep
        public static class DimensionNameBean implements Comparable<DimensionNameBean>, Serializable {
            /**
             * d_res_name : 组织维度
             * d_res_rootid : 965332601103451593621
             * d_res_pkid : 096533260110381557724
             * d_res_id : 965332601103451593621
             * d_res_value : user_org
             * sort : 1
             */

            private String d_res_name;
            private String d_res_rootid;
            private String d_res_pkid;
            private String d_res_id;
            private String d_res_value;
            private Integer sort;
            private Boolean isSelected = false ;

            public Boolean getSelected() {
                return isSelected;
            }

            public void setSelected(Boolean selected) {
                isSelected = selected;
            }

            public String getD_res_name() {
                return d_res_name;
            }

            public void setD_res_name(String d_res_name) {
                this.d_res_name = d_res_name;
            }

            public String getD_res_rootid() {
                return d_res_rootid;
            }

            public void setD_res_rootid(String d_res_rootid) {
                this.d_res_rootid = d_res_rootid;
            }

            public String getD_res_pkid() {
                return d_res_pkid;
            }

            public void setD_res_pkid(String d_res_pkid) {
                this.d_res_pkid = d_res_pkid;
            }

            public String getD_res_id() {
                return d_res_id;
            }

            public void setD_res_id(String d_res_id) {
                this.d_res_id = d_res_id;
            }

            public String getD_res_value() {
                return d_res_value;
            }

            public void setD_res_value(String d_res_value) {
                this.d_res_value = d_res_value;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            @Override
            public int compareTo(DimensionNameBean o) {
                return o.sort;
            }
        }
    }
}
