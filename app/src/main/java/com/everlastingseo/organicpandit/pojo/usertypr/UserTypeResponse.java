package com.everlastingseo.organicpandit.pojo.usertypr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserTypeResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<UserTypeData> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<UserTypeData> getData() {
        return data;
    }

    public void setData(List<UserTypeData> data) {
        this.data = data;
    }
}
