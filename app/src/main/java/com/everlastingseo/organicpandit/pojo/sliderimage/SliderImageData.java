package com.everlastingseo.organicpandit.pojo.sliderimage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderImageData {
    @SerializedName("slider_image")
    @Expose
    private String sliderImage;

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }
}
