package com.wadektech.chips.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantPaymentCompletionRes {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("bankRefInfo")
    @Expose
    private String bankRefInfo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("gratuityAmount")
    @Expose
    private Integer gratuityAmount;
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
    @SerializedName("payerRefInfo")
    @Expose
    private String payerRefInfo;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("systemRefInfo")
    @Expose
    private String systemRefInfo;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;

    public MerchantPaymentCompletionRes(Integer amount, String bankRefInfo, String description,
                                        Integer gratuityAmount, String payeeAccountUuid, String payeeRefInfo,
                                        String payeeSiteName, String payeeSiteRefInfo, String payerRefInfo,
                                        String paymentDate, String reason, String requestId, String status,
                                        String systemRefInfo, String tokenId) {
        this.amount = amount;
        this.bankRefInfo = bankRefInfo;
        this.description = description;
        this.gratuityAmount = gratuityAmount;
        this.payeeAccountUuid = payeeAccountUuid;
        this.payeeRefInfo = payeeRefInfo;
        this.payeeSiteName = payeeSiteName;
        this.payeeSiteRefInfo = payeeSiteRefInfo;
        this.payerRefInfo = payerRefInfo;
        this.paymentDate = paymentDate;
        this.reason = reason;
        this.requestId = requestId;
        this.status = status;
        this.systemRefInfo = systemRefInfo;
        this.tokenId = tokenId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getBankRefInfo() {
        return bankRefInfo;
    }

    public String getDescription() {
        return description;
    }

    public Integer getGratuityAmount() {
        return gratuityAmount;
    }

    public String getPayeeAccountUuid() {
        return payeeAccountUuid;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public String getPayeeSiteName() {
        return payeeSiteName;
    }

    public String getPayeeSiteRefInfo() {
        return payeeSiteRefInfo;
    }

    public String getPayerRefInfo() {
        return payerRefInfo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getReason() {
        return reason;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public String getSystemRefInfo() {
        return systemRefInfo;
    }

    public String getTokenId() {
        return tokenId;
    }
}
