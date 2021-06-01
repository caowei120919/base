package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description : 维度
 */
@Keep
public class DimensionBean {

    /**
     * defaultkey : user_org
     * id : 965332601103451593621
     * nodes : [{"defaultkey":"user_org","id":"14860367656855969470","nodes":[{"defaultkey":"user_org","id":"24992167968453710046","pid":"14860367656855969470","res_level":2,"res_level_sort":0,"rid":"965332601103451593621","text":"管理创新事业部","value":"D-1"},{"defaultkey":"user_org","id":"14883460379602917474","pid":"14860367656855969470","res_level":2,"res_level_sort":1,"rid":"965332601103451593621","text":"大数据事业部","value":"D-2"},{"defaultkey":"user_org","id":"24988510953860096287","pid":"14860367656855969470","res_level":2,"res_level_sort":2,"rid":"965332601103451593621","text":"战略发展部","value":"D-3"},{"defaultkey":"user_org","id":"66655027409736395867","nodes":[{"defaultkey":"user_org","id":"3128313019172355004239","pid":"66655027409736395867","res_level":3,"res_level_sort":0,"rid":"965332601103451593621","text":"业财融合部","value":"D-ZJ-YCRH"},{"defaultkey":"user_org","id":"3128315812388677702446","pid":"66655027409736395867","res_level":3,"res_level_sort":1,"rid":"965332601103451593621","text":"信息管理部","value":"D-ZJ-XXGL"}],"pid":"14860367656855969470","res_level":2,"res_level_sort":3,"rid":"965332601103451593621","text":"浙江子公司","value":"D-ZJ"},{"defaultkey":"user_org","id":"66653088127666091280","pid":"14860367656855969470","res_level":2,"res_level_sort":6,"rid":"965332601103451593621","text":"北京分公司","value":"D-BJ"},{"defaultkey":"user_org","id":"345375277066276786770","pid":"14860367656855969470","res_level":2,"res_level_sort":7,"rid":"965332601103451593621","text":"青岛分公司","value":"D-QD"}],"pid":"965332601103451593621","res_level":0,"res_level_sort":0,"rid":"965332601103451593621","text":"数聚股份","value":"DATACVG"}]
     * pid : 0
     * res_level : 0
     * res_level_sort : 30
     * rid : 965332601103451593621
     * text : 组织维度
     * value : user_org
     */

    private String defaultkey;
    private String id;
    private List<DimensionBean> nodes;
    private String pid;
    private Integer res_level;
    private Integer res_level_sort;
    private String rid;
    private String text;
    private String value;

    public String getDefaultkey() {
        return defaultkey;
    }

    public void setDefaultkey(String defaultkey) {
        this.defaultkey = defaultkey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DimensionBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<DimensionBean> nodes) {
        this.nodes = nodes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getRes_level() {
        return res_level;
    }

    public void setRes_level(Integer res_level) {
        this.res_level = res_level;
    }

    public Integer getRes_level_sort() {
        return res_level_sort;
    }

    public void setRes_level_sort(Integer res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
