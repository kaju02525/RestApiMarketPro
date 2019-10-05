package com.marketpro.user.controller;


import com.marketpro.user.model.ShopRegisterModel;
import com.marketpro.user.model.VenderModel;
import com.marketpro.user.service.vender.VenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/store")
public class VenderController {

    @Autowired
    private VenderService venderService;


    @GetMapping("/check-vender")
    public ResponseEntity<?> venderVerify(@RequestParam String uid) {
        return venderService.venderVerify(uid);
    }

    @PostMapping("/vender-register")
    public ResponseEntity<?> vender(@Valid @RequestBody VenderModel vender) {
        return venderService.venderRegister(vender);
    }

    @PostMapping(value = "/shop-register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> shopRegister(@RequestPart("store_data") @Valid ShopRegisterModel registerModel,
                                          @RequestPart("store_avatar") @Valid @NotNull MultipartFile store_avatar) {
        return venderService.shopRegister(registerModel, store_avatar);
    }

    @PostMapping(value = "/gallery-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> galleryUpload(@RequestParam("vender_id") String vender_id,
                                           @RequestParam("gallery_store") MultipartFile gallery_store) {
        return venderService.galleryUpload(vender_id,gallery_store);
    }

    @GetMapping("/get-gallery")
    public ResponseEntity<?> getGallery(@RequestParam("vender_id") String vender_id) {
        return venderService.getGallery(vender_id);
    }

    @GetMapping("/remove-gallery")
    public ResponseEntity<?> removeGallery(@RequestParam("avatar_id") String avatar_id,
                                           @RequestParam("vender_id") String vender_id) {
        return venderService.removeGallery(avatar_id,vender_id);
    }

    @GetMapping("/all-vender")
    public ResponseEntity<?> getVenderAll() {
        return venderService.getVenderAll();
    }

    @GetMapping("/all-store")
    public ResponseEntity<?> getStoreAll() {
        return venderService.getStoreAll();
    }

    @GetMapping("/get-store")
    public ResponseEntity<?> getStore(@RequestParam("category_id") String category_id) {
        return venderService.getStore(category_id);
    }

    @GetMapping("/get-store-details")
    public ResponseEntity<?> getStoreDetails(@RequestParam("vender_id") String vender_id) {
        return venderService.getStoreDetails(vender_id);
    }

    @GetMapping("/nofifications")
    public ResponseEntity<?> getNotifications(@RequestParam("uid") String uid) {
        return venderService.getNotifications(uid);
    }



    @GetMapping("/image-store/{path}")
    @ResponseBody
    public ResponseEntity<?> getPhoto(@PathVariable("path") String path) throws IOException {
        return venderService.getPhoto(path);
    }


/*
    @PostMapping("/update-category")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> updateCategory(
            @RequestParam("category_id") String category_id,
            @RequestParam("category_name") String category_name,
            @RequestParam("category_desc") String category_desc,
            @RequestParam("avatar_small") MultipartFile avatar_small,
            @RequestParam("avatar_large") MultipartFile avatar_large) {

        if (category_name.isEmpty()) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "category cannot be empty", null), HttpStatus.BAD_REQUEST);
        } else if (category_name.length() < 3) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "name must not be less than 3 characters", null), HttpStatus.BAD_REQUEST);
        } else if (!Pattern.matches("^[a-zA-Z]+$", category_name)) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "Please enter valid category name", null), HttpStatus.BAD_REQUEST);
        } else if (avatar_small.isEmpty()) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "Please upload small image", null), HttpStatus.BAD_REQUEST);
        }else if (avatar_large.isEmpty()) {
            return new ResponseEntity<>(new ResponseObjectModel(false, "Please upload large image", null), HttpStatus.BAD_REQUEST);
        } else {
            return category.updateCategory(category_id,category_name, category_desc, avatar_small, avatar_large);
        }
    }


    @ApiOperation(value = "Get All Category records")
    @GetMapping(value = "/get-category")
    public ResponseEntity<?> findAllCategory() {
        return category.findAllCategory();
    }


    @GetMapping("/image-category/{path}")
    @ResponseBody
    public ResponseEntity<?> getPhoto(@PathVariable("path") String path) throws IOException {
        return category.getPhoto(path);
    }


    @GetMapping("/get-category/{category_id}")
    public ResponseEntity<?> findByCategory(@PathVariable String category_id) {
        return category.findByCategory(category_id);
    }

    @GetMapping("/remove-category-avatar")
    public ResponseEntity<?> removeCategoryAvatar(
            @RequestParam String avatar_key) {
        return category.removeCategoryAvatar(avatar_key);
    }
*/

}
