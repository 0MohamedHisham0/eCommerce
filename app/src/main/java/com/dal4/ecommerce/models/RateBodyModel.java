package com.dal4.ecommerce.models;

public class RateBodyModel {
    Float rating;
    String comment;

    public RateBodyModel(Float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
