package com.everlastingseo.organicpandit.pojo.certificate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CertificteResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CertificateData> getData() {
        return data;
    }

    public void setData(List<CertificateData> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private List<CertificateData> data;




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


}
