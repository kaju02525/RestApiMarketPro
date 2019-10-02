package com.marketpro.user.controller;


import com.marketpro.user.service.nearby.NearbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nearby")
public class NearbyController {

    @Autowired
    private NearbyService nearbyService;


    @GetMapping("/get-nearby")
    public ResponseEntity<?> nearBy(@RequestParam("latitude") Double latitude,
                                    @RequestParam("longitude") Double longitude,
                                    @RequestParam("uid") String uid) {
        return nearbyService.nearBy(uid, latitude, longitude);
    }
}
