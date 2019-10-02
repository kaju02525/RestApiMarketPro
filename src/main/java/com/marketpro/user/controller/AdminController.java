package com.marketpro.user.controller;


import com.marketpro.user.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/verify")
    public ResponseEntity<?> getStoreDetails(@RequestParam("uid") String uid,
                                             @RequestParam("vender_id") String vender_id,
                                             @RequestParam("is_verify") int is_verify) {
        return adminService.isVerify(uid,vender_id,is_verify);
    }

    @GetMapping("/get-pending-vender")
    public ResponseEntity<?> adminService() {
        return adminService.adminService();
    }
}
