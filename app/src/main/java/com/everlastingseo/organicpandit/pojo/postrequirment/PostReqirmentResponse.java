package com.everlastingseo.organicpandit.pojo.postrequirment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostReqirmentResponse implements Serializable {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    @SerializedName("total_count")
    @Expose
    private String total_count;
    @SerializedName("total_page")
    @Expose
    private String total_page;


    @SerializedName("data")
    @Expose
    private List<PostRequirmentData> data = null;

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

    public List<PostRequirmentData> getData() {
        return data;
    }

    public void setData(List<PostRequirmentData> data) {
        this.data = data;
    }
}
