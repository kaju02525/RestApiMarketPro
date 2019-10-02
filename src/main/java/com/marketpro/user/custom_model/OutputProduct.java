package com.marketpro.user.custom_model;

import org.springframework.data.mongodb.core.mapping.Field;

public class OutputProduct {

    @Field("_id")
    private String product_id;
    private String subcategory_id;
    private String category_id;
    private String product_name;
    private String subcategory_name;
    private String category_name;
    private String product_desc;
    private String product_features;
    private String product_unique_no;
    private Double product_price;
    private int product_unit;
    private String product_avatar_small;
    private String product_avatar_large;
    private String gallery_avatar1;
    private String gallery_avatar2;
    private String gallery_avatar3;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String _id) {
        this.product_id = _id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_features() {
        return product_features;
    }

    public void setProduct_features(String product_features) {
        this.product_features = product_features;
    }

    public String getProduct_unique_no() {
        return product_unique_no;
    }

    public void setProduct_unique_no(String product_unique_no) {
        this.product_unique_no = product_unique_no;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(int product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_avatar_small() {
        return product_avatar_small;
    }

    public void setProduct_avatar_small(String product_avatar_small) {
        this.product_avatar_small = product_avatar_small;
    }

    public String getProduct_avatar_large() {
        return product_avatar_large;
    }

    public void setProduct_avatar_large(String product_avatar_large) {
        this.product_avatar_large = product_avatar_large;
    }

    public String getGallery_avatar1() {
        return gallery_avatar1;
    }

    public void setGallery_avatar1(String gallery_avatar1) {
        this.gallery_avatar1 = gallery_avatar1;
    }

    public String getGallery_avatar2() {
        return gallery_avatar2;
    }

    public void setGallery_avatar2(String gallery_avatar2) {
        this.gallery_avatar2 = gallery_avatar2;
    }

    public String getGallery_avatar3() {
        return gallery_avatar3;
    }

    public void setGallery_avatar3(String gallery_avatar3) {
        this.gallery_avatar3 = gallery_avatar3;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}

