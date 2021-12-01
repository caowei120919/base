package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-30
 * @Description :
 */
@Keep
public class ReportTrashEvent {
    private Boolean isEdit = false ;
    public ReportTrashEvent(boolean isEdit) {
        this.isEdit = isEdit ;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }
}
