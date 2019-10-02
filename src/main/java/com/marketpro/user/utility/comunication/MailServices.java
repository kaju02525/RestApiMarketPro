package com.marketpro.user.utility.comunication;

import com.marketpro.user.utility.comunication.model.EmailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


//https://myaccount.google.com/u/0/lesssecureapps?pli=1&pageId=none
class MailServices {
    //@Value("${gmail.username}")
    private String username="karunkumar1447@gmail.com";
    //@Value("${gmail.password}")
    private String password="karun@123";


    void sendEmail(EmailMessage emailMessage){
        Properties props=new Properties();
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.starttls.required",true);
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout","5000");
        props.put("mail.smtp.timeout","5000");
        props.put("mail.smtp.writetimeout","5000");
        props.put("mail.smtp.port","587");

        Session session=Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        Message msg=new MimeMessage(session);
        try {
            InternetAddress[] myToList = InternetAddress.parse(emailMessage.getTo_address());
            msg.setFrom(new InternetAddress(username,false));
            msg.setRecipients(Message.RecipientType.TO,myToList);
            msg.setSubject(emailMessage.getSubject());
            msg.setContent(emailMessage.getBody(),"text/html");
            msg.setSentDate(new Date());


            //Attachment

           /* MimeBodyPart msgBodyPart=new MimeBodyPart();
            msgBodyPart.setContent(emailMessage.getBody(),"text/html");

            Multipart multipart=new MimeMultipart();
            multipart.addBodyPart(msgBodyPart);
            MimeBodyPart attachPart=new MimeBodyPart();

            attachPart.attachFile("C:\\Users\\Datta\\Desktop\\mobile\\ind.jpg");
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);
*/
            Transport.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}

