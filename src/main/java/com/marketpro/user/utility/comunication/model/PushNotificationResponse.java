package com.marketpro.user.utility.comunication.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotificationResponse {
    private int status;
    private String message;
}
