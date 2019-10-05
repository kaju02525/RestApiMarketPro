package com.marketpro.user.service.vender;

import com.marketpro.user.custom_model.OutputVerifyVender;
import com.marketpro.user.custom_model.ResponseArrayModel;
import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.custom_model.ResponseObjectModel;
import com.marketpro.user.exception.CustomException;
import com.marketpro.user.model.GalleryModel;
import com.marketpro.user.model.NotificationModel;
import com.marketpro.user.model.ShopRegisterModel;
import com.marketpro.user.model.VenderModel;
import com.marketpro.user.model.authentication.User;
import com.marketpro.user.model.custom_model.StoreDetailsModel;
import com.marketpro.user.model.custom_model.StoreModel;
import com.marketpro.user.utils.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static com.marketpro.user.utils.Utils.getImageLoad;

@Service
public class VenderServiceImpl implements VenderService {

    private final Logger log = LoggerFactory.getLogger(VenderServiceImpl.class);
    private final Path storePath = StorageProperties.getInstance().getSorePath();

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public ResponseEntity<?> venderVerify(String uid) {
        VenderModel vender = mongoTemplate.findOne(new Query(Criteria.where("uid").is(uid.trim())), VenderModel.class);
        if (vender == null) {
            return new ResponseEntity<>(new ResponseObjectModel(true, "first time create store", new VenderModel()), HttpStatus.OK);
        } else {
        	 OutputVerifyVender result;
             AggregationOperation lookup = Aggregation.lookup("category", "category_id", "_id", "join");
             AggregationOperation unwind = Aggregation.unwind("join", true);
             AggregationOperation replaceRoot = Aggregation.replaceRoot().withValueOf(ObjectOperators.valueOf("join").mergeWith(Aggregation.ROOT));
             AggregationOperation project = Aggregation.project().andExclude("join");
             AggregationOperation match = Aggregation.match(Criteria.where("uid").is(uid.trim()));
             Aggregation aggregation = Aggregation.newAggregation(lookup, unwind, replaceRoot, project, match);

             result = mongoTemplate.aggregate(aggregation, VenderModel.class, OutputVerifyVender.class).getUniqueMappedResult();
             if (result == null) {
                 return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
             } else {
                 return new ResponseEntity<>(new ResponseObjectModel(true, "your verification success", result), HttpStatus.OK);                 
             }
        	
        	
        }
    }

    @Override
    public ResponseEntity<?> venderRegister(VenderModel vender) {
        User userid = mongoTemplate.findOne(new Query(Criteria.where("uid").is(vender.getUid())), User.class);
        if (userid == null) {
            return new ResponseEntity<>(new ResponseModel(false, "User id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            VenderModel check = mongoTemplate.findOne(new Query(Criteria.where("mobile").is(vender.getMobile().trim())), VenderModel.class);
            if (check != null) {
                return new ResponseEntity<>(new ResponseModel(false, "this vender already exists! please try another"), HttpStatus.BAD_REQUEST);
            } else {
                vender.setIs_verify(1);
                mongoTemplate.save(vender);
                mongoTemplate.save(new NotificationModel(vender.getUid(),"Pending","your verification pending"));
                return new ResponseEntity<>(new ResponseObjectModel(true, "your verification pending", vender), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<?> shopRegister(ShopRegisterModel registerModel, MultipartFile store_avatar) {
        VenderModel vender_id = mongoTemplate.findOne(new Query(Criteria.where("_id").is(registerModel.getVender_id().trim())), VenderModel.class);
        if (vender_id == null) {
            return new ResponseEntity<>(new ResponseModel(false, "Vender id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            ShopRegisterModel check = mongoTemplate.findOne(new Query(Criteria.where("vender_id").is(registerModel.getVender_id().trim())), ShopRegisterModel.class);
            if (check != null) {
                return new ResponseEntity<>(new ResponseModel(false, "shop register already exists! please try another"), HttpStatus.BAD_REQUEST);
            } else {
                String avatarStore = "store_" + UUID.randomUUID() + ".png";
                try {
                    try (InputStream inputStream = store_avatar.getInputStream()) {
                        Files.copy(inputStream, this.storePath.resolve(avatarStore), StandardCopyOption.REPLACE_EXISTING);
                    }
                    registerModel.setStore_avatar(avatarStore);
                    registerModel.setIs_verify(2);
                    mongoTemplate.save(registerModel);
                    mongoTemplate.save(new NotificationModel(registerModel.getUid().trim(),"Register Successfully","Your shop register successfully"));
                    return new ResponseEntity<>(new ResponseObjectModel(true, "shop register successfully", registerModel), HttpStatus.OK);
                } catch (IOException e) {
                    throw new CustomException("Failed to store empty file");
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> getPhoto(String path) {
        Path imaPath = Paths.get(storePath + "\\" + path);
        if (Files.exists(imaPath)) {
            return getImageLoad(imaPath);
        } else {
            return new ResponseEntity<>(new ResponseModel(false, "image path not exists"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getStoreAll() {
        List<ShopRegisterModel> store = mongoTemplate.findAll(ShopRegisterModel.class);
        if (store.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all store", store), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getStore(String category_id) {
        List<StoreModel> result;
        AggregationOperation lookup = Aggregation.lookup("user", "uid", "uid", "join");
        AggregationOperation unwind = Aggregation.unwind("join", true);
        AggregationOperation replaceRoot = Aggregation.replaceRoot().withValueOf(ObjectOperators.valueOf("join").mergeWith(Aggregation.ROOT));
        AggregationOperation project = Aggregation.project().andExclude("join");
        AggregationOperation match = Aggregation.match(Criteria.where("category_id").is(category_id.trim()).and("is_verify").is(2));
        Aggregation aggregation = Aggregation.newAggregation(lookup, unwind, replaceRoot, project, match);

        result = mongoTemplate.aggregate(aggregation, ShopRegisterModel.class, StoreModel.class).getMappedResults();
        if (result.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all store", result), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> getStoreDetails(String vender_id) {
        StoreDetailsModel result;
        AggregationOperation lookup = Aggregation.lookup("user", "uid", "uid", "join");
        AggregationOperation unwind = Aggregation.unwind("join", true);
        AggregationOperation replaceRoot = Aggregation.replaceRoot().withValueOf(ObjectOperators.valueOf("join").mergeWith(Aggregation.ROOT));
        AggregationOperation project = Aggregation.project().andExclude("join");
        AggregationOperation match = Aggregation.match(Criteria.where("vender_id").is(vender_id.trim()));


        AggregationOperation lookup1 = Aggregation.lookup("category", "category_id", "_id", "join");
        AggregationOperation unwind1 = Aggregation.unwind("join", true);
        AggregationOperation replaceRoot1 = Aggregation.replaceRoot().withValueOf(ObjectOperators.valueOf("join").mergeWith(Aggregation.ROOT));
        AggregationOperation project1 = Aggregation.project().andExclude("join");

        Aggregation aggregation = Aggregation.newAggregation(lookup,lookup1, unwind,unwind1, replaceRoot,replaceRoot1, project,project1, match);

        
        result = mongoTemplate.aggregate(aggregation, ShopRegisterModel.class, StoreDetailsModel.class).getUniqueMappedResult();
        if (result == null) {
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
        } else {
            List<GalleryModel> list = mongoTemplate.find(new Query(Criteria.where("vender_id").is(vender_id)), GalleryModel.class);
            result.setGallery_list(list);
            return new ResponseEntity<>(new ResponseObjectModel(true, "list all store", result), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> galleryUpload(String vender_id, MultipartFile gallery_store) {
        VenderModel venderId = mongoTemplate.findOne(new Query(Criteria.where("_id").is(vender_id.trim())), VenderModel.class);
        if (venderId == null) {
            return new ResponseEntity<>(new ResponseModel(false, "Vender id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {

            String avatarGallery = "gallery_" + UUID.randomUUID() + ".png";
            try {
                try (InputStream inputStream = gallery_store.getInputStream()) {
                    Files.copy(inputStream, this.storePath.resolve(avatarGallery), StandardCopyOption.REPLACE_EXISTING);
                }
                GalleryModel galleryModel = new GalleryModel();
                galleryModel.setAvatar_id(galleryModel.getAvatar_id());
                galleryModel.setVender_id(vender_id);
                galleryModel.setGallery_avatar(avatarGallery);
                mongoTemplate.save(galleryModel);
                return new ResponseEntity<>(new ResponseObjectModel(true, "Gallery store successfully", galleryModel), HttpStatus.OK);
            } catch (IOException e) {
                throw new CustomException("Failed to store empty file");
            }
        }
    }

    @Override
    public ResponseEntity<?> getGallery(String vender_id) {
        List<GalleryModel> gallery = mongoTemplate.find(new Query(Criteria.where("vender_id").is(vender_id.trim())), GalleryModel.class);
        if (gallery.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "Please upload store gallery images"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all gallery", gallery), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeGallery(String avatar_id,String vender_id) {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        GalleryModel model;
        criteria.andOperator(Criteria.where("_id").is(avatar_id.trim()), Criteria.where("vender_id").is(vender_id.trim()));
        model = mongoTemplate.findOne(query, GalleryModel.class);
        if (model == null) {
            return new ResponseEntity<>(new ResponseModel(false, "avatar id/vender id invalid! please try again"), HttpStatus.BAD_REQUEST);
        } else {
            try {
                if (model.getGallery_avatar() != null && !model.getGallery_avatar().equals("")) {
                    Path imaPath = Paths.get(storePath + "\\" + model.getGallery_avatar());
                    FileSystemUtils.deleteRecursively(imaPath.toFile());
                }
                mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is(avatar_id.trim())), GalleryModel.class);
                return new ResponseEntity<>(new ResponseModel(true, "this gallery image deleted"), HttpStatus.OK);
            } catch (Exception e) {
                throw new CustomException("Failed to gallery empty file");
            }
        }
    }

    @Override
    public ResponseEntity<?> getNotifications(String uid) {
        List<NotificationModel> notification = mongoTemplate.find(new Query(Criteria.where("uid").is(uid.trim())), NotificationModel.class);
        if (notification.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "user id invalid! please try again"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all notifications", notification), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getVenderAll() {
        List<VenderModel> vender = mongoTemplate.findAll(VenderModel.class);
        if (vender.isEmpty())
            return new ResponseEntity<>(new ResponseArrayModel(false, "no data available"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ResponseArrayModel(true, "list all vender", vender), HttpStatus.OK);
    }
}
