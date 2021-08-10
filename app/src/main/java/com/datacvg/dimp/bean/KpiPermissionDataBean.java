package com.datacvg.dimp.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-06
 * @Description :
 */
@Keep
public class KpiPermissionDataBean implements Serializable {

    /**
     * kpiPermission : {"result":"Y-B","sandTablePermission":true,"thresholdName":{"challenge":"挑战值","minimum":"保底","target":"目标值"}}
     */

    private KpiPermissionBean kpiPermission;

    public KpiPermissionBean getKpiPermission() {
        return kpiPermission;
    }

    public void setKpiPermission(KpiPermissionBean kpiPermission) {
        this.kpiPermission = kpiPermission;
    }

    @Keep
    public static class KpiPermissionBean implements Serializable{
        /**
         * result : Y-B
         * sandTablePermission : true
         * thresholdName : {"challenge":"挑战值","minimum":"保底","target":"目标值"}
         */

        private String result;
        private Boolean sandTablePermission;
        private ThresholdNameBean thresholdName;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public Boolean getSandTablePermission() {
            return sandTablePermission;
        }

        public void setSandTablePermission(Boolean sandTablePermission) {
            this.sandTablePermission = sandTablePermission;
        }

        public ThresholdNameBean getThresholdName() {
            return thresholdName;
        }

        public void setThresholdName(ThresholdNameBean thresholdName) {
            this.thresholdName = thresholdName;
        }

        public static class ThresholdNameBean implements Serializable{
            /**
             * challenge : 挑战值
             * minimum : 保底
             * target : 目标值
             */

            private String challenge;
            private String minimum;
            private String target;

            public String getChallenge() {
                return challenge;
            }

            public void setChallenge(String challenge) {
                this.challenge = challenge;
            }

            public String getMinimum() {
                return minimum;
            }

            public void setMinimum(String minimum) {
                this.minimum = minimum;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }
        }
    }
}
