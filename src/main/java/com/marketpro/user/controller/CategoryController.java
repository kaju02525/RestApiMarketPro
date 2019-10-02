package com.marketpro.user.controller;


import com.marketpro.user.custom_model.ResponseModel;
import com.marketpro.user.custom_model.ResponseObjectModel;
import com.marketpro.user.service.category.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService category;



    @PostMapping("/create-category")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> createCategory(
            @RequestParam("category_name") String category_name,
            @RequestParam("category_avatar") MultipartFile category_avatar) {
        if (category_name.isEmpty()) {
            return new ResponseEntity<>(new ResponseModel(false, "category cannot be empty"), HttpStatus.BAD_REQUEST);
        } else if (category_name.length() < 3) {
            return new ResponseEntity<>(new ResponseModel(false, "name must not be less than 3 characters"), HttpStatus.BAD_REQUEST);
        } else if (Pattern.matches("^[a-zA-Z]+$ ", category_name)) {
            return new ResponseEntity<>(new ResponseModel(false, "Please enter valid category name"), HttpStatus.BAD_REQUEST);
        } else if (category_avatar.isEmpty()) {
            return new ResponseEntity<>(new ResponseModel(false, "Please upload category image"), HttpStatus.BAD_REQUEST);
        }else {
            return category.categorySave(category_name,category_avatar);
        }
    }

    @PostMapping("/update-category")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> updateCategory(
            @RequestParam("category_id") String category_id,
            @RequestParam("category_name") String category_name,
            @RequestParam("category_avatar") MultipartFile category_avatar) {

        if (category_name.isEmpty()) {
            return new ResponseEntity<>(new ResponseModel(false, "category cannot be empty"), HttpStatus.BAD_REQUEST);
        } else if (category_name.length() < 3) {
            return new ResponseEntity<>(new ResponseModel(false, "name must not be less than 3 characters"), HttpStatus.BAD_REQUEST);
        } else if (Pattern.matches("^[a-zA-Z]+$ ", category_name)) {
            return new ResponseEntity<>(new ResponseModel(false, "Please enter valid category name"), HttpStatus.BAD_REQUEST);
        } else if (category_avatar.isEmpty()) {
            return new ResponseEntity<>(new ResponseModel(false, "Please upload small image"), HttpStatus.BAD_REQUEST);
        } else {
            return category.updateCategory(category_id,category_name, category_avatar);
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

}
