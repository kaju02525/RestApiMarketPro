package com.marketpro.user.custom_model;

import java.util.Date;

import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class OutputVerifyVender {
	@Field("_id")
    private String vender_id;
    private String uid;
    private String category_id;
    private String category_name;
    private String fullname;
    private String mobile;
    private int is_verify;
    private int category_postion;
    private Date create_at;
    private Date update_at;
}

