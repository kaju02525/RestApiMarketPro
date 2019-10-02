package com.marketpro.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.marketpro.user.utils.Utils.generateOTP;

@Data
public class OtpVerifyModel {

    @NotNull(message = "Mobile number cannot be null")
    @Size(min = 10,max = 10,message = "Mobile number must be 10 digits")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Please enter valid mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "OTP cannot be null")
    @Size(min = 6,max = 6,message = "OTP must be 6 digits")
    @Pattern(regexp="(^$|[0-9]{6})", message = "Please enter valid OTP must be 6 digits")
    private String otp;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_at=new Date();

}
