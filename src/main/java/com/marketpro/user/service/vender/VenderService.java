package com.marketpro.user.service.vender;

import com.marketpro.user.model.ShopRegisterModel;
import com.marketpro.user.model.VenderModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Configuration
public interface VenderService {
    ResponseEntity<?> venderVerify(String uid);

    ResponseEntity<?> venderRegister(VenderModel vender);

    ResponseEntity<?> shopRegister(ShopRegisterModel registerModel, MultipartFile store_avatar);

    ResponseEntity<?> getVenderAll();

    ResponseEntity<?> getPhoto(String path) throws IOException;

    ResponseEntity<?> getStoreAll();

    ResponseEntity<?> getStore(String category);

    ResponseEntity<?> getStoreDetails(String vender_id);

    ResponseEntity<?> galleryUpload(String vender_id, MultipartFile gallery_store);

    ResponseEntity<?> getGallery(String vender_id);

    ResponseEntity<?> removeGallery(String avatar_id,String vender_id);

    ResponseEntity<?> getNotifications(String uid);
}