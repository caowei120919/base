package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-09
 * @Description :
 */
@Keep
public class CommentListBean {
    private List<CommentBean> CommentInfoList ;

    public List<CommentBean> getCommentInfoList() {
        return CommentInfoList;
    }

    public void setCommentInfoList(List<CommentBean> commentInfoList) {
        CommentInfoList = commentInfoList;
    }
}
