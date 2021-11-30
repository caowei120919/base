package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.tree.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-29
 * @Description :
 */
@Keep
public class ChooseContactForActionBean extends Node<ChooseContactForActionBean> implements Serializable {
    private ContactOrDepartmentBean bean ;

    public ChooseContactForActionBean(int id, int pId, int level) {
        super(id, pId, level);
    }
}
