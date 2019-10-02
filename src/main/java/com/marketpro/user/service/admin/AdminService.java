package com.marketpro.user.service.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public interface AdminService {
    ResponseEntity<?> isVerify(String uid,String vender_id, int is_verify);

    ResponseEntity<?> adminService();
}