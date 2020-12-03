package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-27
 * @Description :
 */
@Keep
public class ServiceBean implements Serializable {

    /**
     * message : success
     * status : 1
     * android-resdata : {"httpServer":"http://192.168.2.132/mobile","fisServer":"http://192.168.2.132/api","webServer":"","appVersion":"3.0.2","appStandardVersion":"3.0.2","updateURL":"http://semp.datacvg.com/mob/dev/3.0.2/SEMPMobile_dev.apk","updateStandardURL":"http://semp.datacvg.com/mob/standard/3.0.2/SEMPMobile_standard.apk"}
     * resdata : {"httpSever":"http://192.168.2.132/mobile","fisServer":"http://192.168.2.132/api","webSever":"","appVersion":"3.0.2","appStandardVersion":"3.0.2","updateURL_pad":"itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev_pad/dist/SEMPMobile_dev_pad.plist","updateStandardURL_pad":"itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev_pad/dist/SEMPMobile_dev_pad.plist","updateURL":"itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev/dist/SEMPMobile_dev.plist","updateStandardURL":"itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev/dist/SEMPMobile_dev.plist"}
     * h5Resdata : {"httpServer":"http://192.168.2.132/mobile","h5diServer":"http://192.168.2.132/mobile","fisServer":"http://192.168.2.132/api","webServer":"","appVersion":"1.0.0_base","updateURL":""}
     */

    private String message;
    private int status;
    @SerializedName("android-resdata")
    private AndroidresdataBean androidresdata;
    private ResdataBean resdata;
    private H5ResdataBean h5Resdata;

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

    public AndroidresdataBean getAndroidresdata() {
        return androidresdata;
    }

    public void setAndroidresdata(AndroidresdataBean androidresdata) {
        this.androidresdata = androidresdata;
    }

    public ResdataBean getResdata() {
        return resdata;
    }

    public void setResdata(ResdataBean resdata) {
        this.resdata = resdata;
    }

    public H5ResdataBean getH5Resdata() {
        return h5Resdata;
    }

    public void setH5Resdata(H5ResdataBean h5Resdata) {
        this.h5Resdata = h5Resdata;
    }

    public static class AndroidresdataBean {
        /**
         * httpServer : http://192.168.2.132/mobile
         * fisServer : http://192.168.2.132/api
         * webServer :
         * appVersion : 3.0.2
         * appStandardVersion : 3.0.2
         * updateURL : http://semp.datacvg.com/mob/dev/3.0.2/SEMPMobile_dev.apk
         * updateStandardURL : http://semp.datacvg.com/mob/standard/3.0.2/SEMPMobile_standard.apk
         */

        private String httpServer;
        private String fisServer;
        private String webServer;
        private String appVersion;
        private int appVersionCode = 1001;
        private String appStandardVersion ;
        private int appStandardVersionCode = 1001;
        private String updateURL;

        public int getAppVersionCode() {
            return appVersionCode;
        }

        public void setAppVersionCode(int appVersionCode) {
            this.appVersionCode = appVersionCode;
        }

        public int getAppStandardVersionCode() {
            return appStandardVersionCode;
        }

        public void setAppStandardVersionCode(int appStandardVersionCode) {
            this.appStandardVersionCode = appStandardVersionCode;
        }

        private String updateStandardURL;

        public String getHttpServer() {
            return httpServer;
        }

        public void setHttpServer(String httpServer) {
            this.httpServer = httpServer;
        }

        public String getFisServer() {
            return fisServer;
        }

        public void setFisServer(String fisServer) {
            this.fisServer = fisServer;
        }

        public String getWebServer() {
            return webServer;
        }

        public void setWebServer(String webServer) {
            this.webServer = webServer;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getAppStandardVersion() {
            return appStandardVersion;
        }

        public void setAppStandardVersion(String appStandardVersion) {
            this.appStandardVersion = appStandardVersion;
        }

        public String getUpdateURL() {
            return updateURL;
        }

        public void setUpdateURL(String updateURL) {
            this.updateURL = updateURL;
        }

        public String getUpdateStandardURL() {
            return updateStandardURL;
        }

        public void setUpdateStandardURL(String updateStandardURL) {
            this.updateStandardURL = updateStandardURL;
        }
    }

    public static class ResdataBean {
        /**
         * httpSever : http://192.168.2.132/mobile
         * fisServer : http://192.168.2.132/api
         * webSever :
         * appVersion : 3.0.2
         * appStandardVersion : 3.0.2
         * updateURL_pad : itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev_pad/dist/SEMPMobile_dev_pad.plist
         * updateStandardURL_pad : itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev_pad/dist/SEMPMobile_dev_pad.plist
         * updateURL : itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev/dist/SEMPMobile_dev.plist
         * updateStandardURL : itms-services:///?action=download-manifest&url=https://gitee.com/semf/ios/raw/v3.1.3_dev/dist/SEMPMobile_dev.plist
         */

        private String httpSever;
        private String fisServer;
        private String webSever;
        private String appVersion;
        private String appStandardVersion;
        private String updateURL_pad;
        private String updateStandardURL_pad;
        private String updateURL;
        private String updateStandardURL;

        public String getHttpSever() {
            return httpSever;
        }

        public void setHttpSever(String httpSever) {
            this.httpSever = httpSever;
        }

        public String getFisServer() {
            return fisServer;
        }

        public void setFisServer(String fisServer) {
            this.fisServer = fisServer;
        }

        public String getWebSever() {
            return webSever;
        }

        public void setWebSever(String webSever) {
            this.webSever = webSever;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getAppStandardVersion() {
            return appStandardVersion;
        }

        public void setAppStandardVersion(String appStandardVersion) {
            this.appStandardVersion = appStandardVersion;
        }

        public String getUpdateURL_pad() {
            return updateURL_pad;
        }

        public void setUpdateURL_pad(String updateURL_pad) {
            this.updateURL_pad = updateURL_pad;
        }

        public String getUpdateStandardURL_pad() {
            return updateStandardURL_pad;
        }

        public void setUpdateStandardURL_pad(String updateStandardURL_pad) {
            this.updateStandardURL_pad = updateStandardURL_pad;
        }

        public String getUpdateURL() {
            return updateURL;
        }

        public void setUpdateURL(String updateURL) {
            this.updateURL = updateURL;
        }

        public String getUpdateStandardURL() {
            return updateStandardURL;
        }

        public void setUpdateStandardURL(String updateStandardURL) {
            this.updateStandardURL = updateStandardURL;
        }
    }

    public static class H5ResdataBean {
        /**
         * httpServer : http://192.168.2.132/mobile
         * h5diServer : http://192.168.2.132/mobile
         * fisServer : http://192.168.2.132/api
         * webServer :
         * appVersion : 1.0.0_base
         * updateURL :
         */

        private String httpServer;
        private String h5diServer;
        private String fisServer;
        private String webServer;
        private String appVersion;
        private String updateURL;

        public String getHttpServer() {
            return httpServer;
        }

        public void setHttpServer(String httpServer) {
            this.httpServer = httpServer;
        }

        public String getH5diServer() {
            return h5diServer;
        }

        public void setH5diServer(String h5diServer) {
            this.h5diServer = h5diServer;
        }

        public String getFisServer() {
            return fisServer;
        }

        public void setFisServer(String fisServer) {
            this.fisServer = fisServer;
        }

        public String getWebServer() {
            return webServer;
        }

        public void setWebServer(String webServer) {
            this.webServer = webServer;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getUpdateURL() {
            return updateURL;
        }

        public void setUpdateURL(String updateURL) {
            this.updateURL = updateURL;
        }
    }
}
