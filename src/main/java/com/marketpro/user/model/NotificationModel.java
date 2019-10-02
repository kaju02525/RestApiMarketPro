package com.marketpro.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notification")
public class NotificationModel {

    @Id
    private String notification_id;
    private String uid;
    private String title;
    private String message;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_at=new Date();

    public NotificationModel(String uid, String title, String message) {
        this.uid = uid;
        this.title = title;
        this.message = message;
    }
}
