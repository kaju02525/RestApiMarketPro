package com.marketpro.user.custom_model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class OutputProduct {

    @Field("_id")
    private String product_id;
    private String subcategory_id;
    private String category_id;
    private String category_name;
    private String product_name;
    private String subcategory_name;
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

}

