
package com.everlastingseo.organicpandit.pojo.fetchuserdata_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMicroList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type_id")
    @Expose
    private String userTypeId;
    @SerializedName("element")
    @Expose
    private String element;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("user_type_name")
    @Expose
    private String userTypeName;
    @SerializedName("element_name")
    @Expose
    private String elementName;
    @SerializedName("percentage_name")
    @Expose
    private String percentageName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getPercentageName() {
        return percentageName;
    }

    public void setPercentageName(String percentageName) {
        this.percentageName = percentageName;
    }

}
