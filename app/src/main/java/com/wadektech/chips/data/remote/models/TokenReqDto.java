package com.wadektech.chips.data.remote.models;


import com.google.gson.annotations.SerializedName;

public class TokenReqDto {
    private String requestId;
    private String dueDate;
    private String description;
    private String expiryTime;
    private int amount;
    private String payeeRefInfo;
    private boolean requestTokenImage;
    private String tokenImageSize;

    public TokenReqDto() {
    }

    public TokenReqDto(String requestId, String dueDate, String description, String expiryTime, int amount, String payeeRefInfo, boolean requestTokenImage, String tokenImageSize) {
        this.requestId = requestId;
        this.dueDate = dueDate;
        this.description = description;
        this.expiryTime = expiryTime;
        this.amount = amount;
        this.payeeRefInfo = payeeRefInfo;
        this.requestTokenImage = requestTokenImage;
        this.tokenImageSize = tokenImageSize;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public void setPayeeRefInfo(String payeeRefInfo) {
        this.payeeRefInfo = payeeRefInfo;
    }

    public boolean isRequestTokenImage() {
        return requestTokenImage;
    }

    public void setRequestTokenImage(boolean requestTokenImage) {
        this.requestTokenImage = requestTokenImage;
    }

    public String getTokenImageSize() {
        return tokenImageSize;
    }

    public void setTokenImageSize(String tokenImageSize) {
        this.tokenImageSize = tokenImageSize;
    }
}