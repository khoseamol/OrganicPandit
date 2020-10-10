package com.everlastingseo.organicpandit.pojo.sellproductdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellProductDetailsResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SellproductDetailsResponseData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SellproductDetailsResponseData getData() {
        return data;
    }

    public void setData(SellproductDetailsResponseData data) {
        this.data = data;
    }
}
