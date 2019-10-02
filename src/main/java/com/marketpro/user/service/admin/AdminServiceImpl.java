package com.marketpro.user.service.admin;

import com.marketpro.user.custom_model.ResponseArrayModel;
import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.model.NotificationModel;
import com.marketpro.user.model.ShopRegisterModel;
import com.marketpro.user.model.VenderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.marketpro.user.utility.comunication.CastanetsCommunications.sendMail;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public ResponseEntity<?> isVerify(String uid,String vender_id, int is_verify) {
        String message="";
        VenderModel vendeId = mongoTemplate.findOne(new Query(Criteria.where("_id").is(vender_id.trim()).and("uid").is(uid.trim())), VenderModel.class);
        if (vendeId == null) {
            return new ResponseEntity<>(new ResponseModel(false, "User id/Vender id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            Query query1 = new Query();
            query1.addCriteria(Criteria.where("_id").is(vender_id.trim()));
            Update updateVenderRegister = new Update().set("is_verify", is_verify);
            mongoTemplate.upsert(query1, updateVenderRegister, VenderModel.class);

            ShopRegisterModel checkIsKey = mongoTemplate.findOne(new Query(Criteria.where("vender_id").is(vender_id.trim())), ShopRegisterModel.class);
            if(checkIsKey !=null) {
                Query query2 = new Query();
                query2.addCriteria(Criteria.where("vender_id").is(vender_id.trim()));
                Update updateRegisterShop = new Update().set("is_verify", is_verify);
                mongoTemplate.upsert(query2, updateRegisterShop, ShopRegisterModel.class);
            }
            if(is_verify==1){
                message="Your shop is pending...";
                mongoTemplate.save(new NotificationModel(uid,"Pending",message));
                sendMail("karunkumar02525@gmail.com","Pending",message);
            }
            if(is_verify==2){
                message="Your shop has been active successfully";
                mongoTemplate.save(new NotificationModel(uid,"Approved",message));
                sendMail("karunkumar02525@gmail.com","Approved",message);
            }
            if(is_verify==3){
                message="Your shop has been rejected";
                mongoTemplate.save(new NotificationModel(uid,"Rejected",message));
                sendMail("karunkumar02525@gmail.com","Rejected",message);
            }
            return new ResponseEntity<>(new ResponseModel(true, message), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> adminService() {
        List<VenderModel> vender = mongoTemplate.findAll(VenderModel.class);
        if (vender.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all pending vender", vender), HttpStatus.OK);
    }
}
