package com.droidchat.DroidChat.service.Impl;

import com.droidchat.DroidChat.dto.ChatDTO;
import com.droidchat.DroidChat.dto.ChatObj;
import com.droidchat.DroidChat.dto.ChatRequestDTO;
import com.droidchat.DroidChat.dto.ChatSummaryDTO;
import com.droidchat.DroidChat.model.ChatModel;
import com.droidchat.DroidChat.repository.ChatRepository;
import com.droidchat.DroidChat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

    @Value("${gemini.api.key}")  // fetch gemini_api_key value from application.properties
    private String geminiApiKey;


    @Override
    public String handleChat(ChatRequestDTO requestData) {
        // Step 1 -> Call Gemini API
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + geminiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", requestData.getPrompt_input());

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gemini-2.0-flash");
        body.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                GEMINI_API_URL, HttpMethod.POST, entity, Map.class
        );

        // Step 2 -> Extract response text
        String aiResponse = "No Response";
        try {
            aiResponse = ((Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0))
                    .get("message").toString();
        } catch (Exception e) {
            aiResponse = "Error reading AI response";
        }

        // Step 3 -> Create chat object
        ChatObj newObj = new ChatObj(requestData.getPrompt_input(), aiResponse);

        // Step 4 -> Save or Update DB
        if (requestData.getChat_id_input() == null || requestData.getChat_id_input().isEmpty()) {
            // Create new chat
            ChatModel newChat = new ChatModel();
            newChat.setChatName(requestData.getChat_name_input());
            newChat.setUserId(requestData.getUser_id_input());
            newChat.setChatLogs(new ArrayList<>(List.of(newObj)));
            newChat.setTimestamp(LocalDateTime.now());
            chatRepository.save(newChat);
        } else {
            // Update existing chat
            Optional<ChatModel> existingChat = chatRepository.findById(requestData.getChat_id_input());
            if (existingChat.isPresent()) {
                ChatModel chat = existingChat.get();
                chat.getChatLogs().add(newObj);
                chatRepository.save(chat);
            } else {
                return "Chat ID not found!";
            }
        }

        // Step 5 -> Return success message
        return "Successfully saved chat message in database";
    }

    @Override
    public List<ChatSummaryDTO> getChatSummariesByUserId(String userId) {
        List<ChatModel> chats = chatRepository.findByUserId(userId);

        // Map only required fields (chat_id, chat_name, timestamp)
        return chats.stream()
                .map(chat -> new ChatSummaryDTO(
                        chat.getId(),
                        chat.getChatName(),
                        chat.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ChatDTO getChatById(String chatId) {
        ChatModel chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found with id: " + chatId));

        return chat.convertToChatDTO();

    }

    @Override
    public void deleteChatById(String chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new RuntimeException("Chat not found with id: " + chatId);
        }
        chatRepository.deleteById(chatId);
    }

    @Override
    public String renameChat(String chatId, String updatedChatName) {
        ChatModel chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found with id: " + chatId));

        chat.setChatName(updatedChatName);
        chatRepository.save(chat);

        return "Chat renamed successfully";

    }


}


