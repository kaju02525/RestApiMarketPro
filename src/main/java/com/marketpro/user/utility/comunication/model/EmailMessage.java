package com.marketpro.user.utility.comunication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailMessage {
    private String to_address;
    private String subject;
    private String body;
}
