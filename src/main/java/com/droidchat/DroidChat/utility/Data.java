package com.droidchat.DroidChat.utility;

public class Data {
    public static String otpEmailBody(String otp, String username){
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8' />"
                + "<title>Your DroidChat.AI OTP Code</title>"
                + "</head>"
                + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>"
                + "<tr>"
                + "<td style='padding: 20px; text-align: center; background-color: #22d3ee; border-top-left-radius: 8px; border-top-right-radius: 8px;'>"
                + "<h2 style='margin: 0; color: #333333;'>DroidChat.AI</h2>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 30px;'>"
                + "<h3 style='color: #333333;'>Your OTP Code</h3>"
                + "<p style='color: #555555;'>Hello "+ username +",</p>"
                + "<p style='color: #555555;'>Use the following One-Time Password (OTP) to complete your action. This OTP is valid for 5 minutes.</p>"
                + "<p style='text-align: center;'>"
                + "<span style='display: inline-block; font-size: 24px; letter-spacing: 4px; font-weight: bold; background-color: #f0f0f0; padding: 10px 20px; border-radius: 4px; color: #333333;'>"
                + otp
                + "</span>"
                + "</p>"
                + "<p style='color: #555555;'>If you did not request this, please ignore this email.</p>"
                + "<p style='color: #555555;'>Thanks,<br />DroidChat.AI Team</p>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 10px; text-align: center; background-color: #f0f0f0; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px; color: #999999; font-size: 12px;'>"
                + "© 2025 DroidChat.AI - All rights reserved."
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "</html>";

    }
}
