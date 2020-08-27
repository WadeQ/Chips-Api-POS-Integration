package com.wadektech.chips.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenReqDto {
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expiryTime")
    @Expose
    private String expiryTime;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("payeeCategory1")
    @Expose
    private String payeeCategory1;
    @SerializedName("payeeCategory2")
    @Expose
    private String payeeCategory2;
    @SerializedName("payeeCategory3")
    @Expose
    private String payeeCategory3;
    @SerializedName("payeeRefInfo")
    @Expose
    private String payeeRefInfo;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("siteRefInfo")
    @Expose
    private String siteRefInfo;
    @SerializedName("requestTip")
    @Expose
    private Boolean requestTip;
    @SerializedName("useOnce")
    @Expose
    private Boolean useOnce;
    @SerializedName("notifyUrl")
    @Expose
    private String notifyUrl;
    @SerializedName("requestTokenImage")
    @Expose
    private Boolean requestTokenImage;
    @SerializedName("tokenImageSize")
    @Expose
    private String tokenImageSize;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayeeCategory1() {
        return payeeCategory1;
    }

    public void setPayeeCategory1(String payeeCategory1) {
        this.payeeCategory1 = payeeCategory1;
    }

    public String getPayeeCategory2() {
        return payeeCategory2;
    }

    public void setPayeeCategory2(String payeeCategory2) {
        this.payeeCategory2 = payeeCategory2;
    }

    public String getPayeeCategory3() {
        return payeeCategory3;
    }

    public void setPayeeCategory3(String payeeCategory3) {
        this.payeeCategory3 = payeeCategory3;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public void setPayeeRefInfo(String payeeRefInfo) {
        this.payeeRefInfo = payeeRefInfo;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteRefInfo() {
        return siteRefInfo;
    }

    public void setSiteRefInfo(String siteRefInfo) {
        this.siteRefInfo = siteRefInfo;
    }

    public Boolean getRequestTip() {
        return requestTip;
    }

    public void setRequestTip(Boolean requestTip) {
        this.requestTip = requestTip;
    }

    public Boolean getUseOnce() {
        return useOnce;
    }

    public void setUseOnce(Boolean useOnce) {
        this.useOnce = useOnce;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Boolean getRequestTokenImage() {
        return requestTokenImage;
    }

    public void setRequestTokenImage(Boolean requestTokenImage) {
        this.requestTokenImage = requestTokenImage;
    }

    public String getTokenImageSize() {
        return tokenImageSize;
    }

    public void setTokenImageSize(String tokenImageSize) {
        this.tokenImageSize = tokenImageSize;
    }

}
