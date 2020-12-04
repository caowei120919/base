package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-08
 * @Description :
 */
@Keep
public class DefaultUserBean {

    /**
     * d_res_rootid : 965332601103451593621
     * res_level_sort : 30
     * d_res_id : 965332601103451593621
     * d_res_parentid : 0
     * d_res_flname : OrgDimension
     * d_res_pkid : 965332601103815577248
     * d_res_clname : 组织维度
     * user : [{"user_id":"wenfang","name":"wenfang","id":"2157568307521455612207"},{"user_id":"iOSTest","name":"iOSTest","id":"2306568418922430997294"},{"user_id":"495","name":"495","id":"2763914664618823887206"},{"user_id":"613","name":"613","id":"2763919543030425333871"},{"user_id":"414","name":"414","id":"2772807884495718088291"},{"user_id":"437","name":"437","id":"2893269265492620704929"},{"user_id":"868","name":"868","id":"2893273635517476995943"},{"user_id":"532","name":"532","id":"2893293626269561427322"},{"user_id":"891","name":"891","id":"2893327941167063655842"},{"user_id":"894","name":"894","id":"3012876841856689472272"},{"user_id":"hahaha","name":"???","id":"3686353768154050564424"},{"user_id":"chenqinghua","name":"陈庆华","id":"1029888105912050938100"},{"user_id":"235","name":"235","id":"1091829188793749675469"},{"user_id":"chenqinghua1","name":"陈庆华","id":"1157807232243261419840"},{"user_id":"iosapp","name":"iosapp","id":"1289054664197769177773"},{"user_id":"chentya","name":"chentya","id":"14456956816823888042"},{"user_id":"xmjl","name":"xmjl","id":"277023316092394903299"},{"user_id":"bmjl","name":"bmjl","id":"277026611355727868046"},{"user_id":"yg","name":"yg","id":"277038640939904234801"},{"user_id":"xzy1993","name":"xzy1993","id":"138860869247303776609"},{"user_id":"Ricardo.Zhao","name":"赵俊杰","id":"166256268847690627753"},{"user_id":"574","name":"574","id":"321553923332058904586"},{"user_id":"zjj2020","name":"zjj2020","id":"434883742698856719316"}]
     * res_level : 0
     */

    private String d_res_rootid;
    private int res_level_sort;
    private String d_res_id;
    private String d_res_parentid;
    private String d_res_flname;
    private String d_res_pkid;
    private String d_res_clname;
    private int res_level;
    private List<UserBean> user;

    public String getD_res_rootid() {
        return d_res_rootid;
    }

    public void setD_res_rootid(String d_res_rootid) {
        this.d_res_rootid = d_res_rootid;
    }

    public int getRes_level_sort() {
        return res_level_sort;
    }

    public void setRes_level_sort(int res_level_sort) {
        this.res_level_sort = res_level_sort;
    }

    public String getD_res_id() {
        return d_res_id;
    }

    public void setD_res_id(String d_res_id) {
        this.d_res_id = d_res_id;
    }

    public String getD_res_parentid() {
        return d_res_parentid;
    }

    public void setD_res_parentid(String d_res_parentid) {
        this.d_res_parentid = d_res_parentid;
    }

    public String getD_res_flname() {
        return d_res_flname;
    }

    public void setD_res_flname(String d_res_flname) {
        this.d_res_flname = d_res_flname;
    }

    public String getD_res_pkid() {
        return d_res_pkid;
    }

    public void setD_res_pkid(String d_res_pkid) {
        this.d_res_pkid = d_res_pkid;
    }

    public String getD_res_clname() {
        return d_res_clname;
    }

    public void setD_res_clname(String d_res_clname) {
        this.d_res_clname = d_res_clname;
    }

    public int getRes_level() {
        return res_level;
    }

    public void setRes_level(int res_level) {
        this.res_level = res_level;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * user_id : wenfang
         * name : wenfang
         * id : 2157568307521455612207
         */

        private String user_id;
        private String name;
        private String id;
        private boolean checked = false ;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
