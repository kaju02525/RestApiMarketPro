package com.marketpro.user.model.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AJwtRequest {
    private String mobile;
    private String password;
}

