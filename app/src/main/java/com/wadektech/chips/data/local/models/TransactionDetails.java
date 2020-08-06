package com.wadektech.chips.data.local.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "transaction_details")
public class TransactionDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("modifiedBy")
    @Expose
    private String modifiedBy;
    @SerializedName("payerUuid")
    @Expose
    private String payerUuid;
    @SerializedName("payeeSiteRefInfo")
    @Expose
    private String payeeSiteRefInfo;
    @SerializedName("systemRefInfo")
    @Expose
    private String systemRefInfo;
    @SerializedName("txRefInfo")
    @Expose
    private String txRefInfo;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("gratuityAmount")
    @Expose
    private Integer gratuityAmount;
    @SerializedName("feeAmount")
    @Expose
    private Integer feeAmount;
    @SerializedName("feeVatAmount")
    @Expose
    private Integer feeVatAmount;

    public TransactionDetails(String uuid, String requestId, String created, String createdBy, String lastModified,
                              String modifiedBy, String payerUuid, String payeeSiteRefInfo, String systemRefInfo,
                              String txRefInfo, Integer amount, Integer gratuityAmount, Integer feeAmount, Integer feeVatAmount) {
        this.uuid = uuid;
        this.requestId = requestId;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.modifiedBy = modifiedBy;
        this.payerUuid = payerUuid;
        this.payeeSiteRefInfo = payeeSiteRefInfo;
        this.systemRefInfo = systemRefInfo;
        this.txRefInfo = txRefInfo;
        this.amount = amount;
        this.gratuityAmount = gratuityAmount;
        this.feeAmount = feeAmount;
        this.feeVatAmount = feeVatAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getPayerUuid() {
        return payerUuid;
    }

    public void setPayerUuid(String payerUuid) {
        this.payerUuid = payerUuid;
    }

    public String getPayeeSiteRefInfo() {
        return payeeSiteRefInfo;
    }

    public void setPayeeSiteRefInfo(String payeeSiteRefInfo) {
        this.payeeSiteRefInfo = payeeSiteRefInfo;
    }

    public String getSystemRefInfo() {
        return systemRefInfo;
    }

    public void setSystemRefInfo(String systemRefInfo) {
        this.systemRefInfo = systemRefInfo;
    }

    public String getTxRefInfo() {
        return txRefInfo;
    }

    public void setTxRefInfo(String txRefInfo) {
        this.txRefInfo = txRefInfo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getGratuityAmount() {
        return gratuityAmount;
    }

    public void setGratuityAmount(Integer gratuityAmount) {
        this.gratuityAmount = gratuityAmount;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getFeeVatAmount() {
        return feeVatAmount;
    }

    public void setFeeVatAmount(Integer feeVatAmount) {
        this.feeVatAmount = feeVatAmount;
    }
}
