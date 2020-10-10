package com.everlastingseo.organicpandit.model;

public class DashboardServiceModel {
    String servicename;
    int serviceImage;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    String userID;
    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public int getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(int serviceImage) {
        this.serviceImage = serviceImage;
    }

    public DashboardServiceModel(String userid,String servicename, int serviceImage) {
        this.servicename = servicename;
        this.serviceImage = serviceImage;
        this.userID=userid;
    }
}
