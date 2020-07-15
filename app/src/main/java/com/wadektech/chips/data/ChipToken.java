package com.wadektech.chips.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChipToken implements Parcelable {
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("amount")
    @Expose
    private Long amount;
    @SerializedName("payeeRefInfo")
    @Expose
    private String payeeRefInfo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("requestTokenImage")
    @Expose
    private Boolean requestTokenImage;

    public ChipToken(String requestId, Long amount, String payeeRefInfo, String description,
                     Boolean requestTokenImage) {
        this.requestId = requestId;
        this.amount = amount;
        this.payeeRefInfo = payeeRefInfo;
        this.description = description;
        this.requestTokenImage = requestTokenImage;
    }

    protected ChipToken(Parcel in) {
        requestId = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readLong();
        }
        payeeRefInfo = in.readString();
        description = in.readString();
        byte tmpRequestTokenImage = in.readByte();
        requestTokenImage = tmpRequestTokenImage == 0 ? null : tmpRequestTokenImage == 1;
    }

    public static final Creator<ChipToken> CREATOR = new Creator<ChipToken>() {
        @Override
        public ChipToken createFromParcel(Parcel in) {
            return new ChipToken(in);
        }

        @Override
        public ChipToken[] newArray(int size) {
            return new ChipToken[size];
        }
    };

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setPayeeRefInfo(String payeeRefInfo) {
        this.payeeRefInfo = payeeRefInfo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequestTokenImage(Boolean requestTokenImage) {
        this.requestTokenImage = requestTokenImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestId);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(payeeRefInfo);
        dest.writeString(description);
        dest.writeByte((byte) (requestTokenImage == null ? 0 : requestTokenImage ? 1 : 2));
    }
}
