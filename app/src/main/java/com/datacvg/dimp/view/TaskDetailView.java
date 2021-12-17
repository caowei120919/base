package com.datacvg.dimp.view;

import com.datacvg.dimp.baseandroid.mvp.MvpView;
import com.datacvg.dimp.bean.TaskInfoBean;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-17
 * @Description :
 */
public interface TaskDetailView extends MvpView {
    /**
     * 获取行动方案详情成功
     * @param resdata
     */
    void getTaskInfoSuccess(TaskInfoBean resdata);

    /**
     * 操作成功说明
     */
    void operateTaskSuccess();
}
