package com.droidchat.DroidChat.dto;

import lombok.Data;

@Data
public class ChatRequestDTO {
    private String chat_id_input;
    private String user_id_input;
    private String chat_name_input;
    private String prompt_input;
}
