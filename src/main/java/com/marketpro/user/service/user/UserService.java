package com.marketpro.user.service.user;

import com.marketpro.user.model.OTPModel;
import com.marketpro.user.model.OtpVerifyModel;
import com.marketpro.user.model.authentication.ChangePassword;
import com.marketpro.user.model.authentication.ForgotPassword;
import com.marketpro.user.model.authentication.LoginUser;
import com.marketpro.user.model.authentication.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public interface UserService {
    ResponseEntity<?> createUser(User user);

    ResponseEntity<?> getUser(int page, int size, String search, String sort, String order, String gender);

    ResponseEntity<?> login(LoginUser user);

    ResponseEntity<?> forgotPassword(ForgotPassword forgotPassword);

    ResponseEntity<?> changePassword(ChangePassword changePassword);

    ResponseEntity<?> getUserAll();

    ResponseEntity<?> editProfile(String uid, MultipartFile user_avatar);

    ResponseEntity<?> getPhoto(String path);

    ResponseEntity<?> sendOTP(OTPModel otp);

    ResponseEntity<?> VerifyOTP(OtpVerifyModel verifyModel);
}