package com.datacvg.sempmobile.baseandroid.greendao.bean;

import androidx.annotation.Keep;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-27
 * @Description :
 */
@Entity
@Keep
public class ModuleInfo {
    /**
     * 设置的模块编号
     */
    @Id(autoincrement = true)
    private long module_id ;

    /**
     * 模块名称
     */
    private String module_name ;

    /**
     * 模块资源id
     */
    private String module_res_id ;

    /**
     * 是否为选中展示的模块
     */
    private boolean module_checked ;

    /**
     * 是否为可用模块
     */
    private boolean module_permission ;

    /**
     * 选中情况的图片资源
     */
    private int module_selected_res ;

    /**
     * 未选中情况下的图片资源
     */
    private int module_normal_res ;

    @Generated(hash = 703675415)
    public ModuleInfo(long module_id, String module_name, String module_res_id,
            boolean module_checked, boolean module_permission,
            int module_selected_res, int module_normal_res) {
        this.module_id = module_id;
        this.module_name = module_name;
        this.module_res_id = module_res_id;
        this.module_checked = module_checked;
        this.module_permission = module_permission;
        this.module_selected_res = module_selected_res;
        this.module_normal_res = module_normal_res;
    }

    @Generated(hash = 188743430)
    public ModuleInfo() {
    }

    public long getModule_id() {
        return this.module_id;
    }

    public void setModule_id(long module_id) {
        this.module_id = module_id;
    }

    public String getModule_name() {
        return this.module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getModule_res_id() {
        return this.module_res_id;
    }

    public void setModule_res_id(String module_res_id) {
        this.module_res_id = module_res_id;
    }

    public boolean getModule_checked() {
        return this.module_checked;
    }

    public void setModule_checked(boolean module_checked) {
        this.module_checked = module_checked;
    }

    public boolean getModule_permission() {
        return this.module_permission;
    }

    public void setModule_permission(boolean module_permission) {
        this.module_permission = module_permission;
    }

    public int getModule_selected_res() {
        return this.module_selected_res;
    }

    public void setModule_selected_res(int module_selected_res) {
        this.module_selected_res = module_selected_res;
    }

    public int getModule_normal_res() {
        return this.module_normal_res;
    }

    public void setModule_normal_res(int module_normal_res) {
        this.module_normal_res = module_normal_res;
    }

    
}
