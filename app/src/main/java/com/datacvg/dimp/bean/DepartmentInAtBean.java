package com.datacvg.dimp.bean;


import com.datacvg.dimp.bean.tree.Node;
import com.datacvg.dimp.greendao.bean.DepartmentBean;

import org.greenrobot.greendao.annotation.Keep;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-09
 * @Description : 附带层级结构的
 */
@Keep
public class DepartmentInAtBean extends Node<DepartmentInAtBean> {
    private DepartmentBean bean ;

    public DepartmentBean getBean() {
        return bean;
    }

    public DepartmentInAtBean(int id, int pId, int level, boolean isExpand, DepartmentBean bean) {
        super(id, pId, level, isExpand);
        this.bean = bean ;
    }
}
