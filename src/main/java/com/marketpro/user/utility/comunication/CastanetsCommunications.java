package com.marketpro.user.utility.comunication;

import com.marketpro.user.utility.comunication.model.EmailMessage;
import com.marketpro.user.utility.comunication.model.OTPEmailMessage;

public class CastanetsCommunications {
    public static void sendMail(String email,String title,String message){
        new Thread(() -> new MailServices().sendEmail(new EmailMessage(email,title,message) )).start();
    }

    public static void sendOTPMail(String title,String message){
        new Thread(() -> new OTPMailServices().sendEmail(new OTPEmailMessage(title,message) )).start();
    }
}
