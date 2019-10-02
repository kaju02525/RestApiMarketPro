package com.marketpro.user.service.nearby;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public interface NearbyService {

    ResponseEntity<?> nearBy(String uid, Double latitude, Double longitude);
}