package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-10
 * @Description :
 */
@Keep
public class ScreenListBean {

    /**
     * data : [{"pkid":"415900908808246763024","screen_id":"415900908808246763024","screen_name":"大屏test","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/415905614246558320559_mini.jpg","create_time":"2020-09-22 21:35:06","sort_val":"","update_user":"1691179324035185017442","update_time":"2020-09-22 21:37:12","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1},{"pkid":"231922743295106776388","screen_id":"231922743295106776388","screen_name":"0901","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/413682049745790072370_mini.jpg","create_time":"2020-09-01 14:32:04","sort_val":"","update_user":"1691179324035185017442","update_time":"2020-09-22 21:32:07","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1},{"pkid":"163018225262046223897","screen_id":"163018225262046223897","screen_name":"大屏1","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"vertical\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/143591231163939551_report.jpg","create_time":"2020-08-24 15:07:59","sort_val":"","update_user":"1691179324035185017442","update_time":"2020-08-24 15:07:59","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1},{"pkid":"138022422490748819803","screen_id":"138022422490748819803","screen_name":"大屏","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/136579367978490193007_mini.jpg","create_time":"2020-08-21 17:42:01","sort_val":"","update_user":"1691179324035185017442","update_time":"2020-08-21 17:42:01","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1},{"pkid":"92367562049147035913","screen_id":"92367562049147035913","screen_name":"0518-01大屏","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/92164740534544406969_mini.jpg","create_time":"2020-05-18 11:09:08","sort_val":"","update_user":"1691179324035185017442","update_time":"2020-05-18 11:11:22","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1},{"pkid":"2740893123162769909118","screen_id":"2740893123162769909118","screen_name":"测试","screen_format":"{\"type\":\"16:9\",\"size\":\"32\",\"width\":\"2000\",\"height\":\"2000\",\"direction\":\"horizontal\"}","screen_attribute":"{\"animationMode\":\"circulation\"}","img_path":"image_read/2893784066356552494320","create_time":"2019-10-25 22:17:35","sort_val":"","update_user":"1691179324035185017442","update_time":"2019-11-12 14:59:50","delete_flag":1,"login_name":"shuting","edit_flag":1,"view_flag":1}]
     * message : 成功
     * status : 2000
     * success : true
     */

    private String message;
    private int status;
    private boolean success;
    private List<ScreenBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ScreenBean> getData() {
        return data;
    }

    public void setData(List<ScreenBean> data) {
        this.data = data;
    }
}
