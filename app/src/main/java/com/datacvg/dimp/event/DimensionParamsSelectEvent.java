package com.datacvg.dimp.event;

import androidx.annotation.Keep;

import com.datacvg.dimp.bean.TableParamInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-13
 * @Description : 维度参数选择
 */
@Keep
public class DimensionParamsSelectEvent {
    private List<TableParamInfoBean.DataSourceBean> dataSourceBeans = new ArrayList<>() ;
    public DimensionParamsSelectEvent(List<TableParamInfoBean.DataSourceBean> dataSourceBeansOfMine) {
        this.dataSourceBeans = dataSourceBeansOfMine ;
    }

    public List<TableParamInfoBean.DataSourceBean> getDataSourceBeans() {
        return dataSourceBeans;
    }

    public void setDataSourceBeans(List<TableParamInfoBean.DataSourceBean> dataSourceBeans) {
        this.dataSourceBeans = dataSourceBeans;
    }
}
