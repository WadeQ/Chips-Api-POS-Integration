package com.wadektech.chips.data.remote.myreq;

import android.os.Parcel;
import android.os.Parcelable;

public class TokenResDto implements Parcelable {
    private String uuid;

    private String requestId;

    private String created;

    private String lastModified;

    private String dueDate;

    private String description;

    private String expiryTime;

    private int amount;

    private String payeeRefInfo;

    private boolean requestTip;

    private boolean useOnce;

    private String status;

    private String tokenId;

    private String tokenImage;

    public TokenResDto() {
    }

    protected TokenResDto(Parcel in) {
        uuid = in.readString();
        requestId = in.readString();
        created = in.readString();
        lastModified = in.readString();
        dueDate = in.readString();
        description = in.readString();
        expiryTime = in.readString();
        amount = in.readInt();
        payeeRefInfo = in.readString();
        requestTip = in.readByte() != 0;
        useOnce = in.readByte() != 0;
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
        dest.writeInt(amount);
        dest.writeString(payeeRefInfo);
        dest.writeByte((byte) (requestTip ? 1 : 0));
        dest.writeByte((byte) (useOnce ? 1 : 0));
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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

    public boolean isRequestTip() {
        return requestTip;
    }

    public void setRequestTip(boolean requestTip) {
        this.requestTip = requestTip;
    }

    public boolean isUseOnce() {
        return useOnce;
    }

    public void setUseOnce(boolean useOnce) {
        this.useOnce = useOnce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    @Override
    public String toString() {
        return "TokenResDto{" +
                "uuid='" + uuid + '\'' +
                ", requestId='" + requestId + '\'' +
                ", created='" + created + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", description='" + description + '\'' +
                ", expiryTime='" + expiryTime + '\'' +
                ", amount=" + amount +
                ", payeeRefInfo='" + payeeRefInfo + '\'' +
                ", requestTip=" + requestTip +
                ", useOnce=" + useOnce +
                ", status='" + status + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", tokenImage='" + tokenImage + '\'' +
                '}';
    }
}
