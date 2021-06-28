package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-17
 * @Description :
 */
@Keep
public class DeletePageBean {

    /**
     * deletePage : true
     */

    private Boolean deletePage;

    public Boolean getDeletePage() {
        return deletePage;
    }

    public void setDeletePage(Boolean deletePage) {
        this.deletePage = deletePage;
    }
}
