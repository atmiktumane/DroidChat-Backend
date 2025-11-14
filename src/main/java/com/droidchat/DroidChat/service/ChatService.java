package com.droidchat.DroidChat.service;

import com.droidchat.DroidChat.dto.ChatDTO;
import com.droidchat.DroidChat.dto.ChatRequestDTO;
import com.droidchat.DroidChat.dto.ChatSummaryDTO;

import java.util.List;

public interface ChatService {
    String handleChat(ChatRequestDTO requestData);

    List<ChatSummaryDTO> getChatSummariesByUserId(String userId);

    ChatDTO getChatById(String chatId);

    void deleteChatById(String chatId);

    String renameChat(String chatId, String updatedChatName);

}
