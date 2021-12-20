package com.datacvg.dimp.bean;

import com.datacvg.dimp.bean.tree.Node;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-17
 * @Description :
 */
public class ActionPlanIndexForActionBean extends Node<ActionPlanIndexForActionBean> {
    private boolean isChecked = false ;
    private ActionPlanIndexBean bean ;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public ActionPlanIndexForActionBean(String id, String pId, int level) {
        super(id, pId, level);
    }

    public ActionPlanIndexBean getBean() {
        return bean;
    }

    public void setBean(ActionPlanIndexBean bean) {
        this.bean = bean;
    }
}
