package com.marketpro.user.service.user;

import com.marketpro.user.custom_model.ResponseArrayModel;
import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.custom_model.ResponseObjectModel;
import com.marketpro.user.exception.CustomException;
import com.marketpro.user.model.OTPModel;
import com.marketpro.user.model.OtpVerifyModel;
import com.marketpro.user.model.authentication.*;
import com.marketpro.user.repository.AUserRepo;
import com.marketpro.user.utils.JwtTokenUtil;
import com.marketpro.user.utils.OtpUtils;
import com.marketpro.user.utils.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.marketpro.user.utility.comunication.CastanetsCommunications.sendOTPMail;
import static com.marketpro.user.utils.Utils.*;

@Service
public class UserServiceImpl implements UserService {

    @Value("${otp.expireTime}")
    private Long otpExpireTime;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Path profilePath = StorageProperties.getInstance().getProfilePath();

    @Autowired
    private AUserRepo repo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



/*
    @Autowired
    private  MailServices mailServices;
    @Autowired
    private  PushNotificationService pushNotificationService;
  */

    @Override
    public ResponseEntity<?> sendOTP(OTPModel model) {
        OTPModel otp;
        User checkUserMobile;

        otp = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(model.getMobile().trim())), OTPModel.class);
        checkUserMobile = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(model.getMobile().trim())), User.class);

        if (otp != null && otp.getIs_verify()==1 && checkUserMobile !=null) {
            return new ResponseEntity<>(new ResponseModel(false, "This mobile number otp verified/already exist"), HttpStatus.CONFLICT);
        }else if (checkUserMobile == null && otp != null && otp.getIs_verify()==1) {
            Map<String,Integer> map=new HashMap<>();
            map.put("is_create",1);
            return new ResponseEntity<>(new ResponseObjectModel(true, "Please complete create account", map), HttpStatus.OK);
        }else {
            long otpExpire=System.currentTimeMillis()+ otpExpireTime;
           // String otpValue=generateOTP();
            String otpValue="111111";
            if(otp==null) {
                model.setOtp(otpValue);
                model.setExpirytime(otpExpire);
                mongoTemplate.save(model);
            }else if(OtpUtils.waitResendOTP(otp.getExpirytime()-otpExpireTime)<=120){
            	int time=OtpUtils.waitResendOTP(otp.getExpirytime()-otpExpireTime);
        
            	String message="Please wait "+ OtpUtils.convertSeconds(time) +"after try again!!";
                return new ResponseEntity<>(new ResponseModel(false, message), HttpStatus.BAD_REQUEST);
            }else {
                Query query2 = new Query();
                query2.addCriteria(Criteria.where("mobile").is(model.getMobile().trim()));
                Update updateOTP = new Update()
                        .set("is_verify", 0)
                        .set("otp",otpValue)
                        .set("expirytime",otpExpire)
                        .set("create_at",model.getCreate_at());
                mongoTemplate.upsert(query2, updateOTP, OTPModel.class);
            }
            
            String mess=model.getOtp() +" is your OTP for verifying your mobile number with MarketPro. This OTP is valid for 1 minute." + "Do not share OTP for security reasons.";
            //sendOTPMail("Your OTP Verification Mobile No: "+model.getMobile(),mess);
            Map<String,Integer> map=new HashMap<>();
            map.put("is_create",0);
            return new ResponseEntity<>(new ResponseObjectModel(true, "OTP send your mail please check", map), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> VerifyOTP(OtpVerifyModel verifyModel) {
        OTPModel field;
        field = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(verifyModel.getMobile().trim())), OTPModel.class);
        if (field == null) {
            return new ResponseEntity<>(new ResponseModel(false, "Your mobile number invalid ! please try again"), HttpStatus.BAD_REQUEST);
        }else if(field.getIs_verify()==1){
            return new ResponseEntity<>(new ResponseModel(false, "Your mobile already verified"), HttpStatus.BAD_REQUEST);
        }else if(!field.getOtp().trim().equals(verifyModel.getOtp().trim())){
            return new ResponseEntity<>(new ResponseModel(false, "Your otp invalid ! please try again"), HttpStatus.BAD_REQUEST);
        }else if(field.getExpirytime()<=System.currentTimeMillis()){
            return new ResponseEntity<>(new ResponseModel(false, "Your otp expiry ! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            Query query2 = new Query();
            query2.addCriteria(Criteria.where("mobile").is(verifyModel.getMobile().trim()));
            Update updateOTP = new Update().set("is_verify", 1).set("otp", "xxxxxx");
            mongoTemplate.upsert(query2, updateOTP, OTPModel.class);
           // sendOTPMail("Your OTP Verification Success","Your otp verify successfully");
            return new ResponseEntity<>(new ResponseModel(true, "Your otp verify successfully"), HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> createUser(User user) {
        String pass = "";
        User checkUser, checkUserMobile;
        OTPModel field;
        checkUserMobile = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(user.getMobile().trim())), User.class);
        field = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(user.getMobile().trim())), OTPModel.class);
        if (field ==null){
            return new ResponseEntity<>(new ResponseModel(false, "Invalid mobile number Please enter verified mobile no"), HttpStatus.BAD_REQUEST);
        }else if (checkUserMobile != null) {
            return new ResponseEntity<>(new ResponseModel(false, "This mobile number already exist"), HttpStatus.CONFLICT);
        } else {
            pass = user.getPassword();
            user.setUid(timeStamp());
            user.setUser_avatar("");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repo.save(user);
            user.setPassword("");

            Authentication auth = authenticate(new AJwtRequest(user.getMobile(), pass));
            final String token = jwtTokenUtil.generateToken(auth);
            user.setToken(token);

            return new ResponseEntity<>(new ResponseObjectModel(true, "Your registration successfully", user), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> login(LoginUser loginUser) {
        Query query = new Query();
        User userData;
        query.addCriteria(Criteria.where("mobile").is(loginUser.getMobile()));
        userData = mongoTemplate.findOne(query, User.class);

        if (userData == null) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "mobile number or password invalid ! please try again", null), HttpStatus.BAD_REQUEST);
        } else {
            Authentication auth = authenticate(new AJwtRequest(loginUser.getMobile(), loginUser.getPassword()));
            final String token = jwtTokenUtil.generateToken(auth);
            userData.setToken(token);
            userData.setPassword("");
            return new ResponseEntity<>(new ResponseObjectModel(true, "Your login successfully", userData), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> forgotPassword(ForgotPassword forgotPass) {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        User user;
        criteria.andOperator(Criteria.where("mobile").is(forgotPass.getMobile().trim()),
                Criteria.where("last_name").is(forgotPass.getLast_name().trim()));
        user = mongoTemplate.findOne(query, User.class);

        if (user == null) {
            return new ResponseEntity<>(new ResponseModel(false, "Your mobile number or last name invalid ! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            query.addCriteria(Criteria.where("mobile").is(forgotPass.getMobile().trim()));
            Update update = new Update().set("password", passwordEncoder.encode(forgotPass.getPassword().trim()));
            mongoTemplate.upsert(query, update, User.class);
            return new ResponseEntity<>(new ResponseModel(true, "Your password reset successfully"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePassword changePass) {
        User user;
        user = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(changePass.getMobile().trim())), User.class);
        if (user == null) {
            return new ResponseEntity<>(new ResponseModel(false, "Your mobile number invalid ! please try again"), HttpStatus.NOT_FOUND);
        } else {
            if (!passwordEncoder.matches(changePass.getCurrent_password(), user.getPassword())) {
                return new ResponseEntity<>(new ResponseModel(false, "Your current password invalid ! please try again"), HttpStatus.NOT_FOUND);
            } else {
                Query query1 = new Query();
                query1.addCriteria(Criteria.where("mobile").is(changePass.getMobile().trim()));
                Update update = new Update().set("password", passwordEncoder.encode(changePass.getNew_password().trim()));
                mongoTemplate.upsert(query1, update, User.class);
                return new ResponseEntity<>(new ResponseModel(true, "your password has been changed successfully"), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<?> getUserAll() {
        List<User> user = mongoTemplate.findAll(User.class);
        if (user.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all users", user), HttpStatus.OK);

    }


    private Authentication authenticate(AJwtRequest authenticationRequest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest, null));
        } catch (DisabledException e) {
            throw new CustomException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new CustomException("INVALID_CREDENTIALS");
        }
    }


    @Override
    public ResponseEntity<?> getUser(int page, int size, String search, String sort, String order, String gender) {
        List<User> user;
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        if (search != null)
            criteria.andOperator(new Criteria().orOperator(
                    Criteria.where("name").regex(search),
                    Criteria.where("mobile").regex(search)));

        if (sort != null && order != null)
            query.with(new Sort(Sort.Direction.valueOf(order.toUpperCase()), sort));

        if (gender != null)
            query.addCriteria(Criteria.where("gender").is(gender));


        query.with(new PageRequest(page - 1, size));
        user = mongoTemplate.find(query, User.class);
        log.info("Show All Data: " + user);

        if (user.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all", user), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> editProfile(String uid, MultipartFile user_avatar) {
        User model = mongoTemplate.findOne(new Query(Criteria.where("uid").is(uid.trim())), User.class);
        if (model == null) {
            return new ResponseEntity<>(new ResponseModel(false, "user id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            if (model.getUser_avatar() != null && !model.getUser_avatar().equals("")) {
                Path imaPath = Paths.get(profilePath + "\\" + model.getUser_avatar());
                FileSystemUtils.deleteRecursively(imaPath.toFile());
            }
            String avatarUser = "profile_" + UUID.randomUUID() + ".png";
            try {
                try (InputStream inputStream = user_avatar.getInputStream()) {
                    Files.copy(inputStream, this.profilePath.resolve(avatarUser), StandardCopyOption.REPLACE_EXISTING);
                }
                Query query = new Query();
                query.addCriteria(Criteria.where("uid").is(uid.trim()));
                Update update = new Update().set("user_avatar", avatarUser);
                mongoTemplate.upsert(query, update, User.class);
                Map<String, String> map = new HashMap<>();
                map.put("uid", uid);
                map.put("user_avatar", avatarUser);
                return new ResponseEntity<>(new ResponseObjectModel(true, "profile has been updated successfully", map), HttpStatus.OK);
            } catch (Exception e) {
                throw new CustomException("Failed to update empty file");
            }
        }
    }

    @Override
    public ResponseEntity<?> getPhoto(String path) {
        Path imaPath = Paths.get(profilePath + "\\" + path);
        if (Files.exists(imaPath)) {
            return getImageLoad(imaPath);
        } else {
            return new ResponseEntity<>(new ResponseModel(false, "image path not exists"), HttpStatus.BAD_REQUEST);
        }
    }


}
