package com.datacvg.dimp.event;

import androidx.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-08
 * @Description :
 */
@Keep
public class DeleteCommentEvent {

    private int position ;
    private boolean deleteAll = false ;

    public boolean isDeleteAll() {
        return deleteAll;
    }

    public void setDeleteAll(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }

    public DeleteCommentEvent(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }

    public DeleteCommentEvent(int position) {
        this.position = position ;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
