package com.everlastingseo.organicpandit.pojo.fetchuserdata_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailsData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_type_id")
    @Expose
    private String userTypeId;
    @SerializedName("partner_type_id")
    @Expose
    private String partnerTypeId;
    @SerializedName("partner_user_id")
    @Expose
    private String partnerUserId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("ceo_name")
    @Expose
    private String ceoName;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("landline_no")
    @Expose
    private String landlineNo;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("certification_number")
    @Expose
    private String certificationNumber;
    @SerializedName("certification_id")
    @Expose
    private String certificationId;
    @SerializedName("total_farmer")
    @Expose
    private String totalFarmer;
    @SerializedName("website")
    @Expose
    private Object website;
    @SerializedName("type_of_buyer")
    @Expose
    private Object typeOfBuyer;
    @SerializedName("gst_number")
    @Expose
    private Object gstNumber;
    @SerializedName("aadhar_number")
    @Expose
    private String aadharNumber;
    @SerializedName("pancard_number")
    @Expose
    private String pancardNumber;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("company_image")
    @Expose
    private String companyImage;
    @SerializedName("certification_image")
    @Expose
    private String certificationImage;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("product_catalogue")
    @Expose
    private String productCatalogue;
    @SerializedName("resume")
    @Expose
    private String resume;
    @SerializedName("is_visit_farm")
    @Expose
    private String isVisitFarm;
    @SerializedName("is_test_report")
    @Expose
    private String isTestReport;
    @SerializedName("is_verified")
    @Expose
    private String isVerified;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_view")
    @Expose
    private String isView;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("user_type_name")
    @Expose
    private String userTypeName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("agency_name")
    @Expose
    private String agencyName;
    @SerializedName("user_product_list")
    @Expose
    private List<UserProductList> userProductList = null;
    @SerializedName("user_crop_list")
    @Expose
    private List<UserCropList> userCropList = null;
    @SerializedName("user_soil_list")
    @Expose
    private List<UserSoilList> userSoilList = null;
    @SerializedName("user_micro_list")
    @Expose
    private List<UserMicroList> userMicroList = null;
    @SerializedName("user_input_list")
    @Expose
    private List<UserInputList> userInputList = null;

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

    public String getPartnerTypeId() {
        return partnerTypeId;
    }

    public void setPartnerTypeId(String partnerTypeId) {
        this.partnerTypeId = partnerTypeId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(String certificationId) {
        this.certificationId = certificationId;
    }

    public String getTotalFarmer() {
        return totalFarmer;
    }

    public void setTotalFarmer(String totalFarmer) {
        this.totalFarmer = totalFarmer;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getTypeOfBuyer() {
        return typeOfBuyer;
    }

    public void setTypeOfBuyer(Object typeOfBuyer) {
        this.typeOfBuyer = typeOfBuyer;
    }

    public Object getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(Object gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPancardNumber() {
        return pancardNumber;
    }

    public void setPancardNumber(String pancardNumber) {
        this.pancardNumber = pancardNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
    }

    public String getCertificationImage() {
        return certificationImage;
    }

    public void setCertificationImage(String certificationImage) {
        this.certificationImage = certificationImage;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getProductCatalogue() {
        return productCatalogue;
    }

    public void setProductCatalogue(String productCatalogue) {
        this.productCatalogue = productCatalogue;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getIsVisitFarm() {
        return isVisitFarm;
    }

    public void setIsVisitFarm(String isVisitFarm) {
        this.isVisitFarm = isVisitFarm;
    }

    public String getIsTestReport() {
        return isTestReport;
    }

    public void setIsTestReport(String isTestReport) {
        this.isTestReport = isTestReport;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public List<UserProductList> getUserProductList() {
        return userProductList;
    }

    public void setUserProductList(List<UserProductList> userProductList) {
        this.userProductList = userProductList;
    }

    public List<UserCropList> getUserCropList() {
        return userCropList;
    }

    public void setUserCropList(List<UserCropList> userCropList) {
        this.userCropList = userCropList;
    }

    public List<UserSoilList> getUserSoilList() {
        return userSoilList;
    }

    public void setUserSoilList(List<UserSoilList> userSoilList) {
        this.userSoilList = userSoilList;
    }

    public List<UserMicroList> getUserMicroList() {
        return userMicroList;
    }

    public void setUserMicroList(List<UserMicroList> userMicroList) {
        this.userMicroList = userMicroList;
    }

    public List<UserInputList> getUserInputList() {
        return userInputList;
    }

    public void setUserInputList(List<UserInputList> userInputList) {
        this.userInputList = userInputList;
    }
}
