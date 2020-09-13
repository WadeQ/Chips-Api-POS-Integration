package com.wadektech.chips.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TokenResDto implements Parcelable {
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;
    @SerializedName("tokenImage")
    @Expose
    private String tokenImage;

    public TokenResDto(String uuid, String requestId, String created, String lastModified, String dueDate,
                       String description, String expiryTime, Double amount, String payeeCategory1,
                       String payeeCategory2, String payeeCategory3, String payeeRefInfo, String siteName,
                       String siteRefInfo, Boolean requestTip, Boolean useOnce, String status, String tokenId,
                       String tokenImage) {
        this.uuid = uuid;
        this.requestId = requestId;
        this.created = created;
        this.lastModified = lastModified;
        this.dueDate = dueDate;
        this.description = description;
        this.expiryTime = expiryTime;
        this.amount = amount;
        this.payeeCategory1 = payeeCategory1;
        this.payeeCategory2 = payeeCategory2;
        this.payeeCategory3 = payeeCategory3;
        this.payeeRefInfo = payeeRefInfo;
        this.siteName = siteName;
        this.siteRefInfo = siteRefInfo;
        this.requestTip = requestTip;
        this.useOnce = useOnce;
        this.status = status;
        this.tokenId = tokenId;
        this.tokenImage = tokenImage;
    }

    protected TokenResDto(Parcel in) {
        uuid = in.readString();
        requestId = in.readString();
        created = in.readString();
        lastModified = in.readString();
        dueDate = in.readString();
        description = in.readString();
        expiryTime = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        payeeCategory1 = in.readString();
        payeeCategory2 = in.readString();
        payeeCategory3 = in.readString();
        payeeRefInfo = in.readString();
        siteName = in.readString();
        siteRefInfo = in.readString();
        byte tmpRequestTip = in.readByte();
        requestTip = tmpRequestTip == 0 ? null : tmpRequestTip == 1;
        byte tmpUseOnce = in.readByte();
        useOnce = tmpUseOnce == 0 ? null : tmpUseOnce == 1;
        status = in.readString();
        tokenId = in.readString();
        tokenImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(requestId);
        dest.writeString(created);
        dest.writeString(lastModified);
        dest.writeString(dueDate);
        dest.writeString(description);
        dest.writeString(expiryTime);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(payeeCategory1);
        dest.writeString(payeeCategory2);
        dest.writeString(payeeCategory3);
        dest.writeString(payeeRefInfo);
        dest.writeString(siteName);
        dest.writeString(siteRefInfo);
        dest.writeByte((byte) (requestTip == null ? 0 : requestTip ? 1 : 2));
        dest.writeByte((byte) (useOnce == null ? 0 : useOnce ? 1 : 2));
        dest.writeString(status);
        dest.writeString(tokenId);
        dest.writeString(tokenImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TokenResDto> CREATOR = new Creator<TokenResDto>() {
        @Override
        public TokenResDto createFromParcel(Parcel in) {
            return new TokenResDto(in);
        }

        @Override
        public TokenResDto[] newArray(int size) {
            return new TokenResDto[size];
        }
    };

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

    public String getExpiryTime() {
        return expiryTime;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPayeeCategory1() {
        return payeeCategory1;
    }

    public String getPayeeCategory2() {
        return payeeCategory2;
    }

    public String getPayeeCategory3() {
        return payeeCategory3;
    }

    public String getPayeeRefInfo() {
        return payeeRefInfo;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteRefInfo() {
        return siteRefInfo;
    }

    public Boolean getRequestTip() {
        return requestTip;
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