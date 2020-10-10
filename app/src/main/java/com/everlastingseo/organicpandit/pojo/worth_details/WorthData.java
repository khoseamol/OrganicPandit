package com.everlastingseo.organicpandit.pojo.worth_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorthData {
    @SerializedName("total_price")
    @Expose
    private String totalPrice;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
