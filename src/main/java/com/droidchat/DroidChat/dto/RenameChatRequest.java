package com.droidchat.DroidChat.dto;

import lombok.Data;

@Data
public class RenameChatRequest {
    private String chatId;
    private String updatedChatName;
}
