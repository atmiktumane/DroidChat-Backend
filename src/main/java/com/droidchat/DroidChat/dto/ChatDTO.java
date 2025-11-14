package com.droidchat.DroidChat.dto;

import com.droidchat.DroidChat.model.ChatModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private String id;
    private String chatName;
    private LocalDateTime timestamp;
    private List<ChatObj> chatLogs;
    private String userId;

    public ChatModel convertToChatModel(){
        return new ChatModel(this.id, this.chatName, this.timestamp, this.chatLogs, this.userId);
    }
}
