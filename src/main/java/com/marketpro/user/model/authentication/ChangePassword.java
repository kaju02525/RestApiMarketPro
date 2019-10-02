package com.marketpro.user.model.authentication;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ChangePassword {

    @NotNull(message = "Mobile number cannot be null")
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "current password cannot be null")
    @Size(min = 8,max = 16,message = "current password must be equal or grater than 8 characters and less than 16 characters")
    private String current_password;

    @NotNull(message = "new password cannot be null")
    @Size(min = 8,max = 16,message = "new password must be equal or grater than 8 characters and less than 16 characters")
    private String new_password;


}
