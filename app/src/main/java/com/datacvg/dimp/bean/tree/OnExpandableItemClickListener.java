package com.datacvg.dimp.bean.tree;

import android.view.View;
import android.widget.AdapterView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-09
 * @Description : 可展开的条目点击时间监听
 */
public interface OnExpandableItemClickListener<T extends Node<T>> {
    /**
     * @param node     被点击条目的数据
     * @param parent   被点击的父容器
     * @param view     被点击的View
     * @param position 被点击条目所在整个数据集合中的索引
     */
    void onExpandableItemClick(T node, AdapterView<?> parent, View view, int position);
}
