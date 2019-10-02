package com.marketpro.user.controller;/*
package com.popwoot.admin.controller;

import com.popwoot.admin.custom_model.ResponseObjectModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController {
    private static final String PATH="error";
    @Override
    public String getErrorPath() {
        return PATH;
    }

    @GetMapping(PATH)
    public ResponseEntity<?> error(){
        return new ResponseEntity<>(new ResponseObjectModel(false, "Please pass valid jwt token.", null), HttpStatus.UNAUTHORIZED);
    }

}
*/
