package com.droidchat.DroidChat.model;

import com.droidchat.DroidChat.dto.ChatDTO;
import com.droidchat.DroidChat.dto.ChatObj;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chats")
public class ChatModel {
    @Id
    private String id;
    private String chatName;
    private LocalDateTime timestamp;
    private List<ChatObj> chatLogs;
    private String userId;  // Foreign Key to User Collection

    public ChatDTO convertToChatDTO(){
        return new ChatDTO(this.id, this.chatName, this.timestamp, this.chatLogs, this.userId);
    }
}
