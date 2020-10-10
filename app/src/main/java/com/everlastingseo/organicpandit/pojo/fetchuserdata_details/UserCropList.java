
package com.everlastingseo.organicpandit.pojo.fetchuserdata_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCropList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type_id")
    @Expose
    private String userTypeId;
    @SerializedName("inspector_name")
    @Expose
    private String inspectorName;
    @SerializedName("crop_id")
    @Expose
    private String cropId;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("date_sown")
    @Expose
    private String dateSown;
    @SerializedName("date_harvest")
    @Expose
    private String dateHarvest;
    @SerializedName("date_inspection")
    @Expose
    private String dateInspection;
    @SerializedName("crop_condition")
    @Expose
    private String cropCondition;
    @SerializedName("other_details")
    @Expose
    private String otherDetails;
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
    @SerializedName("crop_name")
    @Expose
    private String cropName;

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

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDateSown() {
        return dateSown;
    }

    public void setDateSown(String dateSown) {
        this.dateSown = dateSown;
    }

    public String getDateHarvest() {
        return dateHarvest;
    }

    public void setDateHarvest(String dateHarvest) {
        this.dateHarvest = dateHarvest;
    }

    public String getDateInspection() {
        return dateInspection;
    }

    public void setDateInspection(String dateInspection) {
        this.dateInspection = dateInspection;
    }

    public String getCropCondition() {
        return cropCondition;
    }

    public void setCropCondition(String cropCondition) {
        this.cropCondition = cropCondition;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
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

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

}
