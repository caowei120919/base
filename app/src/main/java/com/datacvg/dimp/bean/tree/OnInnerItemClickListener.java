package com.datacvg.dimp.bean.tree;

import android.view.View;
import android.widget.AdapterView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-09
 * @Description :   不可展开条目点击事件监听
 * */
public interface OnInnerItemClickListener<T extends Node<T>> {
    /**
     * @param node     被点击条目的数据
     * @param parent   被点击的父容器
     * @param view     被点击的View
     * @param position 被点击条目所在整个数据集合中的索引
     */
    void onClick(T node, AdapterView<?> parent, View view, int position);
}
