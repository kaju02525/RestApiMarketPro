package com.marketpro.user.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static void ImageSave(MultipartFile imagePath, String value) {
        try {
            byte[] bytes = imagePath.getBytes();
            Path path = Paths.get(value);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResponseEntity<?> getImageLoad(Path imaPath ){
        byte[] image = new byte[0];
        HttpHeaders headers;
        try {
            image = Files.readAllBytes(imaPath);
            headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(image, null, HttpStatus.BAD_REQUEST);
        }
    }

    public static String timeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant instant = timestamp.toInstant();
        long times=instant.toEpochMilli();
        return String.valueOf(times);
    }

    public static String distFrom(Double lat1, Double lng1, double[] lat2, double[] lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2[0]-lat1);
        double dLng = Math.toRadians(lng2[1]-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2[0])) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float finaldata= (float) (earthRadius * c)/1000;
        return String.format(Locale.US, "%.2f", finaldata);
    }
    
    public static boolean isEmailValid(String email){
    	boolean isValid = false;
    	/*
    	Email format: A valid email address will have following format:
    	        [\\w\\.-]+: Begins with word characters, (may include periods and hypens).
    		@: It must have a '@' symbol after initial characters.
    		([\\w\\-]+\\.)+: '@' must follow by more alphanumeric characters (may include hypens.).
    	This part must also have a "." to separate domain and subdomain names.
    		[A-Z]{2,4}$ : Must end with two to four alphabets.
    	(This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz)
    	Examples: Following email addresses will pass validation
    	abc@xyz.net; ab.c@tx.gov
    	*/
    	//Initialize regex for email.
    	String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    	CharSequence inputStr = email;
    	//Make the comparison case-insensitive.
    	Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
    	Matcher matcher = pattern.matcher(inputStr);
    	if(matcher.matches()){
    	isValid = true;
    	}
    	return isValid;
    	}

    /** isPhoneNumberValid: Validate phone number using Java reg ex.
    * This method checks if the input string is a valid phone number.
    * @return boolean: true if phone number is valid, false otherwise.
    */
    public static boolean isPhoneNumberValid(String phoneNumber){
    boolean isValid = false;
    /* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn
    	^\\(? : May start with an option "(" .
    	(\\d{3}): Followed by 3 digits.
    	\\)? : May have an optional ")" 
    	[- ]? : May have an optional "-" after the first 3 digits or after optional ) character. 
    	(\\d{3}) : Followed by 3 digits. 
    	 [- ]? : May have another optional "-" after numeric digits.
    	 (\\d{4})$ : ends with four digits.

             Examples: Matches following phone numbers:
             (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890

    */
    //Initialize reg ex for phone number. 
    String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    CharSequence inputStr = phoneNumber;
    Pattern pattern = Pattern.compile(expression);
    Matcher matcher = pattern.matcher(inputStr);
    if(matcher.matches()){
    isValid = true;
    }
    return isValid;
    }


    public static String generateOTP() {
        String numbers = "1234567890";
        StringBuilder otpOut=new StringBuilder();
        Random random = new Random();
        for(int i = 0; i< 6 ; i++) {
            otpOut.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otpOut.toString();
    }

   public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
}
