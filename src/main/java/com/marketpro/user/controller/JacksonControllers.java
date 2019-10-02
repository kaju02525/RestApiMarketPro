package com.marketpro.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.marketpro.user.model.StudetModel;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("demo")
public class JacksonControllers {

    @PostMapping("/submit")
    public StudetModel get(@RequestBody StudetModel model) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File("data/output.json"),model);
        return model;
    }

    @GetMapping("/get")
    public StudetModel getData() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(new File("data/output.json"),StudetModel.class);
    }

}
