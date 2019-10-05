package com.marketpro.user.model.custom_model;

import com.marketpro.user.model.GalleryModel;
import lombok.Data;

import java.util.List;

@Data
public class StoreDetailsModel {
    private String uid;
    private String vender_id;
    private String category_id;
    private String category_name;
    private String shop_name;
    private String shop_mobile;
    private String shop_address;
    private String store_avatar;
    private String user_avatar;
    private String latitude;
    private String longitude;
    private String shop_email;
    private String shop_ownername;
    private String shop_owner_mobile;
    private String shop_owner_email;
    private String shop_nearby;
    private String shop_color;
    private String shop_pincode;
    private List<GalleryModel> gallery_list;
}