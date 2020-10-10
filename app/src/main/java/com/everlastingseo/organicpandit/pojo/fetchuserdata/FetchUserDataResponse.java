package com.everlastingseo.organicpandit.pojo.fetchuserdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchUserDataResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FetchUserData data;

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

    public FetchUserData getData() {
        return data;
    }

    public void setData(FetchUserData data) {
        this.data = data;
    }}
