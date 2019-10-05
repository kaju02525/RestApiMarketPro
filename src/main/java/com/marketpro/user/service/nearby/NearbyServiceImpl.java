package com.marketpro.user.service.nearby;

import com.marketpro.user.custom_model.ResponseArrayModel;
import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.custom_model.ResponseObjectModel;
import com.marketpro.user.model.ShopRegisterModel;
import com.marketpro.user.model.authentication.User;
import com.marketpro.user.utils.StorageProperties;
import com.marketpro.user.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NearbyServiceImpl implements NearbyService {

    private final Logger log = LoggerFactory.getLogger(NearbyServiceImpl.class);
    private final Path storePath = StorageProperties.getInstance().getSorePath();

    @Autowired
    private MongoTemplate mongoTemplate;


    private static final double distance=14;

    @Override
    public ResponseEntity<?> nearBy(String uid, Double latitude, Double longitude) {
        User check = mongoTemplate.findOne(new Query(Criteria.where("uid").is(uid.trim())), User.class);
        if (check == null) {
            return new ResponseEntity<>(new ResponseModel(false, "user id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            Criteria criteria = new Criteria("location").near(new Point(latitude, longitude))
                          .maxDistance(getInKilometer(distance));
            List<ShopRegisterModel> result = mongoTemplate.find(new Query(criteria), ShopRegisterModel.class);
            if (result.isEmpty())  {
                return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
            } else {
                List< Map<String,Object>> list=new ArrayList<>();
                for(ShopRegisterModel p:result) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("shop_name", p.getShop_name());
                    map.put("category", p.getCategory_id());
                    map.put("distance",Utils.distFrom(latitude,longitude,p.getLocation(),p.getLocation())+" KM");
                    map.put("location",p.getLocation());
                    list.add(map);
                }
                return new ResponseEntity<>(new ResponseObjectModel(true, "list size : "+result.size(), list), HttpStatus.OK);
            }
        }
    }


    private Double getInKilometer(Double maxdistance) {
        return maxdistance / 111.0d;
    }
}
