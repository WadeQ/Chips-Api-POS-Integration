package com.wadektech.chips.data.local.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payments {

    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;
    @SerializedName("values")
    @Expose
    private List<PaymentDetails> values;

    public Payments(Integer pageSize, Integer pageNumber, Integer totalPages, Integer totalElements,
                    List<PaymentDetails> values) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.values = values;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<PaymentDetails> getValues() {
        return values;
    }

    public void setValues(List<PaymentDetails> values) {
        this.values = values;
    }
}