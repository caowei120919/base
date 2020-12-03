package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-12
 * @Description :其他维度
 */
@Keep
public class OtherDimensionBean {

    @SerializedName("0")
    private List<DimensionBean> _$0;
    @SerializedName("1")
    private List<DimensionBean> _$1;

    public List<DimensionBean> get_$0() {
        return _$0;
    }

    public void set_$0(List<DimensionBean> _$0) {
        this._$0 = _$0;
    }

    public List<DimensionBean> get_$1() {
        return _$1;
    }

    public void set_$1(List<DimensionBean> _$1) {
        this._$1 = _$1;
    }
}
