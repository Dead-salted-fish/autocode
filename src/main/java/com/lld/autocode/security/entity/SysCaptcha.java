package com.lld.autocode.security.entity;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/10 15:33
 */
public class SysCaptcha {
    private String imgBase64;
    private String captchaIdentification;

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getCaptchaIdentification() {
        return captchaIdentification;
    }

    public void setCaptchaIdentification(String captchaIdentification) {
        this.captchaIdentification = captchaIdentification;
    }

    public SysCaptcha() {
    }

    public SysCaptcha(String imgBase64, String captchaIdentification) {
        this.imgBase64 = imgBase64;
        this.captchaIdentification = captchaIdentification;
    }


}
