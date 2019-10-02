package com.marketpro.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "shop_register")
public class ShopRegisterModel {

    @NotNull(message = "user id cannot be null")
    private String uid;

    @NotNull(message = "vender_id id cannot be null")
    private String vender_id;

    @NotNull(message = "category cannot be null")
    @Size(min = 3, max = 13, message = "category must not be less than 3 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Please enter valid category")
    private String category;

    @NotNull(message = "shop name cannot be null")
    @Size(min = 3, message = "shop name must not be less than 3 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Please enter valid shop name")
    private String shop_name;

    @NotNull(message = "shop email cannot be null")
    @Size(min = 3, max = 30, message = "shop email must not be less than 3 characters")
    @Email(message = "Please enter valid shop email")
    private String shop_email;

    @NotNull(message = "Mobile number cannot be null")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String shop_mobile;

    @NotNull(message = "shop address cannot be null")
    @Size(min = 3, max = 150, message = "shop address must not be less than 3 characters max 150")
    private String shop_address;

    @NotNull(message = "shop nearby cannot be null")
    @Size(min = 3, max = 30, message = "shop nearby must not be less than 3 characters")
    private String shop_nearby;

    @NotNull(message = "Pin code cannot be null")
    @Size(min = 6, max = 6, message = "Pin code must be 6 digits")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Please enter valid shop pin code must be 6 digits")
    private String shop_pincode;

    @NotNull(message = "shop color cannot be null")
    @Size(min = 3, max = 10, message = "shop color must not be less than 3 characters")
    private String shop_color;

    @NotNull(message = "shop owner name cannot be null")
    @Size(min = 3, message = "shop owner name must not be less than 3 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Please enter valid shop owner name")
    private String shop_ownername;

    @NotNull(message = "Owner Mobile number cannot be null")
    @Size(min = 10, max = 10, message = "Owner Mobile number must be 10 digits")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Please enter valid Owner mobile number must be 10 digits")
    private String shop_owner_mobile;

    @NotNull(message = "Owner email cannot be null")
    @Size(min = 3, max = 30, message = "Owner email must not be less than 3 characters")
    @Email(message = "Please enter valid Owner email")
    private String shop_owner_email;

    @NotNull(message = "shop location latitude/longitude cannot be null")
    @GeoSpatialIndexed(type= GeoSpatialIndexType.GEO_2D)
    double[] location;

    @ApiModelProperty(hidden = true)
    private String store_avatar;

    @ApiModelProperty(hidden = true)
    private int is_verify;

    @ApiModelProperty(hidden = true)
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_at=new Date();

    @ApiModelProperty(hidden = true)
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date update_at=new Date();

}

