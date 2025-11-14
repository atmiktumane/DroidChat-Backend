package com.droidchat.DroidChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatSummaryDTO {
    private String chatId;
    private String chatName;
    private LocalDateTime timestamp;
}
