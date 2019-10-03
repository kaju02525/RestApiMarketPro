package com.marketpro.user.model.authentication;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode
@Document(collection = "user")
public class User {

    @ApiModelProperty(hidden = true)
    @Id
    private String uid;

    @ApiModelProperty(hidden = true)
    private String token;

    @NotNull(message = "first name cannot be empty")
    @Size(min = 3,message = "first name must not be less than 3 characters")
    @Pattern(regexp="^[a-zA-Z]+$", message = "Please enter valid first name")
    private String first_name;

    @NotNull(message = "last name cannot be empty")
    @Size(min = 3,message = "last name must not be less than 3 characters")
    @Pattern(regexp="^[a-zA-Z]+$", message = "Please enter valid last name")
    private String last_name;

    @NotNull(message = "Mobile number cannot be empty")
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String mobile;


    @NotNull(message = "gender cannot be empty")
    @ApiModelProperty(allowableValues = "male")
    private String gender;

    @NotNull(message = "password cannot be empty")
    @Size(min = 8,max = 16,message = "Password must be equal or grater than 8 characters and less than 16 characters")
    private String password;

  
    @ApiModelProperty(hidden = true)
    private String user_avatar;


    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_at=new Date();

}
