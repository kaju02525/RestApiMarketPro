package com.marketpro.user.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class OtpUtils {
	
	
    public static int waitResendOTP(long OldTimestamp) {
			
		long diff= System.currentTimeMillis() - OldTimestamp;
		
		return convertHHMMSSToSeconds(timeStamOperation(diff));
			
	}
	

	private static String timeStamOperation(long inputTimestamp) {
		Date date = new Date(inputTimestamp);
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Delhi"));
		return  formatter.format(date);
	}
	
	
	private static int convertHHMMSSToSeconds(String time) {
		//String h="01:00:00";
		//String h="00:00";
		String[] h1=time.split(":");

		//int hour=Integer.parseInt(h1[0]);
		int minute=Integer.parseInt(h1[0]);
		int second=Integer.parseInt(h1[1]);
				 	      				 
		//return second + (60 * minute) + (3600 * hour);	
		return second + (60 * minute);	
	}
	
	
		
	public static String convertSeconds(int sec) {
		int seconds=IntegerOpreation.reverseIntegerOpreation(sec);
		
	    int h = seconds/ 3600;
	    int m = (seconds % 3600) / 60;
	    int s = seconds % 60;
	    String sh = (h > 0 ? String.valueOf(h) + " " + "h" : "");
	    String sm = (m < 10 && m > 0 && h > 0 ? "0" : "") + (m > 0 ? (h > 0 && s == 0 ? String.valueOf(m) : String.valueOf(m) + " " + "minute") : "");
	    String ss = (s == 0 && (h > 0 || m > 0) ? "" : (s < 10 && (h > 0 || m > 0) ? "0" : "") + String.valueOf(s) + " " + "seconds ");
	    return sh + (h > 0 ? " " : "") + sm + (m > 0 ? " " : "") + ss;
	}

}
