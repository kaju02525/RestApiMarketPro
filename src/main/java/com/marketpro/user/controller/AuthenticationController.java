package com.marketpro.user.controller;

import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.model.OTPModel;
import com.marketpro.user.model.OtpVerifyModel;
import com.marketpro.user.model.authentication.ChangePassword;
import com.marketpro.user.model.authentication.ForgotPassword;
import com.marketpro.user.model.authentication.LoginUser;
import com.marketpro.user.model.authentication.User;
import com.marketpro.user.service.user.UserService;
import com.marketpro.user.utils.Utils;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@CrossOrigin
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@Valid @RequestBody OTPModel otp) {
        return userService.sendOTP(otp);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> VerifyOTP(@Valid @RequestBody OtpVerifyModel verifyModel) {
        return userService.VerifyOTP(verifyModel);
    }



    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser) {
        return userService.login(loginUser);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassword forgotPassword) {
        return userService.forgotPassword(forgotPassword);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        return userService.changePassword(changePassword);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getUserAll(){
       return userService.getUserAll();
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<?> editProfile(@RequestParam("uid") String uid,
                                          @RequestParam("user_avatar") MultipartFile user_avatar,
                                          @RequestParam("first_name") String first_name,
                                          @RequestParam("email") String email,
                                          @RequestParam("dob") String dob){
    	

    	 if (uid.isEmpty()) {
             return new ResponseEntity<>(new ResponseModel(false, "uid cannot be empty"), HttpStatus.BAD_REQUEST);
         }else if (first_name.isEmpty()) {
             return new ResponseEntity<>(new ResponseModel(false, "first name cannot be empty"), HttpStatus.BAD_REQUEST);
         }else if (first_name.length() < 3) {
             return new ResponseEntity<>(new ResponseModel(false, "first name must not be less than 3 characters"), HttpStatus.BAD_REQUEST);
         } else if (Pattern.matches("^[a-zA-Z]+$ ", first_name)) {
             return new ResponseEntity<>(new ResponseModel(false, "Please enter valid first name"), HttpStatus.BAD_REQUEST);
         }else if (email.isEmpty()) {
             return new ResponseEntity<>(new ResponseModel(false, "email id cannot be empty"), HttpStatus.BAD_REQUEST);
         }else if (email.length() < 3) {
             return new ResponseEntity<>(new ResponseModel(false, "email id must not be less than 3 characters"), HttpStatus.BAD_REQUEST);
         } else if (!Utils.isEmailValid(email)) {
             return new ResponseEntity<>(new ResponseModel(false, "Please enter valid email id "), HttpStatus.BAD_REQUEST);
         }else if (dob.isEmpty()) {
             return new ResponseEntity<>(new ResponseModel(false, "DOB cannot be empty"), HttpStatus.BAD_REQUEST);
         }else if (dob.length() < 3) {
             return new ResponseEntity<>(new ResponseModel(false, "DOB must not be less than dd/mm/yyyy characters"), HttpStatus.BAD_REQUEST);
         } else if (Pattern.matches("^[a-zA-Z]+$ ", dob)) {
             return new ResponseEntity<>(new ResponseModel(false, "Please enter valid DOB"), HttpStatus.BAD_REQUEST);
         }else {
             return userService.editProfile(uid,user_avatar,first_name,email,dob);
		}
    }
    
    @GetMapping("/image-profile/{path}")
    @ResponseBody
    public ResponseEntity<?> getPhoto(@PathVariable("path") String path) {
        return userService.getPhoto(path);
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@RequestParam("uid") String uid){
    	 if (uid.isEmpty()) {
             return new ResponseEntity<>(new ResponseModel(false, "User id cannot be empty"), HttpStatus.BAD_REQUEST);
         }else {
         	 return userService.getDashboard(uid);
		}
    }
    


    @GetMapping("/get-users")
    public ResponseEntity<?> getUser(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,

            @ApiParam(value = "search for any name")
            @RequestParam(value = "search", required = false) String search,

            @ApiParam(value = "sort by name,mobile")
            @RequestParam(value = "sort", required = false) String sort,

            @ApiParam(value = "Can sorted ASC, DESC")
            @RequestParam(value = "order", required = false) String order,

            @ApiParam(value = "fill the box male or female")
            @RequestParam(value = "gender", required = false) String gender) {
        return userService.getUser(page, size, search, sort, order, gender);
    }
}
