package com.marketpro.user.utility.comunication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OTPEmailMessage {
    private String subject;
    private String body;
}
