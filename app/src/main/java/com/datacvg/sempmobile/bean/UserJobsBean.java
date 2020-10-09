package com.datacvg.sempmobile.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-09
 * @Description : 职位
 */
@Keep
public class UserJobsBean {

    /**
     * user_pkid : 149698005983064815
     * user_id : gxq
     * login_name : gxq
     * user_clname : 郭晓庆
     * post_clname : 青岛分公司负责人
     * login_session_type :
     */

    private String user_pkid;
    private String user_id;
    private String login_name;
    private String user_clname;
    private String post_clname;
    private String login_session_type;

    public String getUser_pkid() {
        return user_pkid;
    }

    public void setUser_pkid(String user_pkid) {
        this.user_pkid = user_pkid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getUser_clname() {
        return user_clname;
    }

    public void setUser_clname(String user_clname) {
        this.user_clname = user_clname;
    }

    public String getPost_clname() {
        return post_clname;
    }

    public void setPost_clname(String post_clname) {
        this.post_clname = post_clname;
    }

    public String getLogin_session_type() {
        return login_session_type;
    }

    public void setLogin_session_type(String login_session_type) {
        this.login_session_type = login_session_type;
    }
}
