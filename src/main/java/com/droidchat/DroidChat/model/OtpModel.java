package com.droidchat.DroidChat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "otp")
public class OtpModel {
    @Id
    private String email;
    private String otpCode;
    private LocalDateTime creationTime;
}
