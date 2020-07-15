package com.wadektech.chips.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChipTokenResponse {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("payeeRefInfo")
    @Expose
    private String payeeRefInfo;
    @SerializedName("useOnce")
    @Expose
    private Boolean useOnce;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;
    @SerializedName("tokenImage")
    @Expose
    private String tokenImage;

    public ChipTokenResponse(String uuid, String requestId, String created, String lastModified,
                             String dueDate, String description, Double amount, String payeeRefInfo,
                             Boolean useOnce, String status, String tokenId, String tokenImage) {
        this.uuid = uuid;
        this.requestId = requestId;
        this.created = created;
        this.lastModified = lastModified;
        this.dueDate = dueDate;
        this.description = description;
        this.amount = amount;
        this.payeeRefInfo = payeeRefInfo;
        this.useOnce = useOnce;
        this.status = status;
        this.tokenId = tokenId;
        this.tokenImage = tokenImage;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getCreated() {
        return created;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public Boolean getUseOnce() {
        return useOnce;
    }

    public String getStatus() {
        return status;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getTokenImage() {
        return tokenImage;
    }
}
