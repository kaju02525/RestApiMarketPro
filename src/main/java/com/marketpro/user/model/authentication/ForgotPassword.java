package com.marketpro.user.model.authentication;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ForgotPassword {

    @NotNull(message = "last name cannot be null")
    @Size(min = 3,message = "last name must not be less than 3 characters")
    @Pattern(regexp="^[a-zA-Z]+$", message = "Please enter valid last name")
    private String last_name;

    @NotNull(message = "Mobile number cannot be null")
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "password cannot be null")
    @Size(min = 8,max = 16,message = "Password must be equal or grater than 8 characters and less than 16 characters")
    private String password;

}
