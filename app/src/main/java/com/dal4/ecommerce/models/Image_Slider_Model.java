package com.dal4.ecommerce.models;

public class Image_Slider_Model {


    // image url is used to
    // store the url of image
    private String imgUrl;

    // Constructor method.
    public Image_Slider_Model(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
