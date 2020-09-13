package com.wadektech.chips.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReceiptReq {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("bankRefInfo")
    @Expose
    private String bankRefInfo;
    @SerializedName("gratuityAmount")
    @Expose
    private Integer gratuityAmount;
    @SerializedName("payeeAccountUuid")
    @Expose
    private String payeeAccountUuid;
    @SerializedName("payeeRefInfo")
    @Expose
    private String payeeRefInfo;
    @SerializedName("payerRefInfo")
    @Expose
    private String payerRefInfo;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setBankRefInfo(String bankRefInfo) {
        this.bankRefInfo = bankRefInfo;
    }

    public void setGratuityAmount(Integer gratuityAmount) {
        this.gratuityAmount = gratuityAmount;
    }

    public void setPayeeAccountUuid(String payeeAccountUuid) {
        this.payeeAccountUuid = payeeAccountUuid;
    }

    public void setPayeeRefInfo(String payeeRefInfo) {
        this.payeeRefInfo = payeeRefInfo;
    }

    public void setPayerRefInfo(String payerRefInfo) {
        this.payerRefInfo = payerRefInfo;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
