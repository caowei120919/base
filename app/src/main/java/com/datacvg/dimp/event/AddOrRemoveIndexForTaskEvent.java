package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.ActionPlanIndexBean;
/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-20
 * @Description :
 */
@Keep
public class AddOrRemoveIndexForTaskEvent {
    private ActionPlanIndexBean actionPlanIndexBean ;

    public AddOrRemoveIndexForTaskEvent(ActionPlanIndexBean actionPlanIndexBean) {
        this.actionPlanIndexBean = actionPlanIndexBean ;
    }

    public ActionPlanIndexBean getActionPlanIndexBean() {
        return actionPlanIndexBean;
    }

    public void setActionPlanIndexBean(ActionPlanIndexBean actionPlanIndexBean) {
        this.actionPlanIndexBean = actionPlanIndexBean;
    }
}
