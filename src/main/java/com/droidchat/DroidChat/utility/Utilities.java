package com.droidchat.DroidChat.utility;

import java.security.SecureRandom;

public class Utilities {
    public static String generateOTP(){
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom(); // Generate Random number having encrypted secure layer

        // Generate OTP with 6 digits
        for(int i=0; i<6; i++){
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
