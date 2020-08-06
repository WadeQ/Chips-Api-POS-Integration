package com.wadektech.chips.data.local.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "payment_details")
public class PaymentDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("expiryTime")
    @Expose
    private String expiryTime;
    @SerializedName("payeeAccountUuid")
    @Expose
    private String payeeAccountUuid;
    @SerializedName("payeeRefInfo")
    @Expose
    private String payeeRefInfo;
    @SerializedName("payeeSiteName")
    @Expose
    private String payeeSiteName;
    @SerializedName("payeeSiteRefInfo")
    @Expose
    private String payeeSiteRefInfo;
    @SerializedName("requestTip")
    @Expose
    private Boolean requestTip;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("systemRefInfo")
    @Expose
    private String systemRefInfo;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;
    @SerializedName("tokenImage")
    @Expose
    private String tokenImage;

    public PaymentDetails(Integer amount, String description, String expiryTime, String payeeAccountUuid,
                          String payeeRefInfo, String payeeSiteName, String payeeSiteRefInfo, Boolean requestTip,
                          String status, String systemRefInfo, String tokenId, String tokenImage) {
        this.amount = amount;
        this.description = description;
        this.expiryTime = expiryTime;
        this.payeeAccountUuid = payeeAccountUuid;
        this.payeeRefInfo = payeeRefInfo;
        this.payeeSiteName = payeeSiteName;
        this.payeeSiteRefInfo = payeeSiteRefInfo;
        this.requestTip = requestTip;
        this.status = status;
        this.systemRefInfo = systemRefInfo;
        this.tokenId = tokenId;
        this.tokenImage = tokenImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public String getPayeeAccountUuid() {
        return payeeAccountUuid;
    }

    public void setPayeeAccountUuid(String payeeAccountUuid) {
        this.payeeAccountUuid = payeeAccountUuid;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public void setPayeeRefInfo(String payeeRefInfo) {
        this.payeeRefInfo = payeeRefInfo;
    }

    public String getPayeeSiteName() {
        return payeeSiteName;
    }

    public void setPayeeSiteName(String payeeSiteName) {
        this.payeeSiteName = payeeSiteName;
    }

    public String getPayeeSiteRefInfo() {
        return payeeSiteRefInfo;
    }

    public void setPayeeSiteRefInfo(String payeeSiteRefInfo) {
        this.payeeSiteRefInfo = payeeSiteRefInfo;
    }

    public Boolean getRequestTip() {
        return requestTip;
    }

    public void setRequestTip(Boolean requestTip) {
        this.requestTip = requestTip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemRefInfo() {
        return systemRefInfo;
    }

    public void setSystemRefInfo(String systemRefInfo) {
        this.systemRefInfo = systemRefInfo;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenImage() {
        return tokenImage;
    }

    public void setTokenImage(String tokenImage) {
        this.tokenImage = tokenImage;
    }
}
