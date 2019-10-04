package com.marketpro.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vender")
public class VenderModel {
    @ApiModelProperty(hidden = true)
    @Id
    private String vender_id;

    @NotNull(message = "user id cannot be empty")
    private String uid;

    @NotNull(message = "fullname cannot be empty")
    @Size(min = 3,message = "fullname must not be less than 3 characters")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "Please enter valid fullname")
    private String fullname;

    @NotNull(message = "Mobile number cannot be empty")
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "category_id cannot be empty")
    @Size(min = 3,message = "category_id must valid characters")
    private String category_id;

    @ApiModelProperty(hidden = true)
    private int is_verify;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_at=new Date();

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date update_at=new Date();



}
