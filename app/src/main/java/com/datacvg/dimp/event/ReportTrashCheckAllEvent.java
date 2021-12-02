package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-01
 * @Description :
 */
@Keep
public class ReportTrashCheckAllEvent {
    private boolean isCheckAll = false ;
    public ReportTrashCheckAllEvent(boolean checked) {
        this.isCheckAll = checked ;
    }

    public boolean isCheckAll() {
        return isCheckAll;
    }

    public void setCheckAll(boolean checkAll) {
        isCheckAll = checkAll;
    }
}
