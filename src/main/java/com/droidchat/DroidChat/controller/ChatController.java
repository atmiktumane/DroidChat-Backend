package com.droidchat.DroidChat.controller;

import com.droidchat.DroidChat.dto.ChatDTO;
import com.droidchat.DroidChat.dto.ChatRequestDTO;
import com.droidchat.DroidChat.dto.ChatSummaryDTO;
import com.droidchat.DroidChat.dto.RenameChatRequest;
import com.droidchat.DroidChat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    // POST - send chat
    @PostMapping("/send")
    public String handleChat(@RequestBody ChatRequestDTO requestData) {
        return chatService.handleChat(requestData);
    }

    // GET: Fetch basic user summary for a user
    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<List<ChatSummaryDTO>> getChatSummariesByUserId(@PathVariable String userId) {
        List<ChatSummaryDTO> summaries = chatService.getChatSummariesByUserId(userId);
        return ResponseEntity.ok(summaries);
    }

    // GET: Fetch full chat details by chat_id
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> getChatById(@PathVariable String chatId) {
        ChatDTO chat = chatService.getChatById(chatId);
        return ResponseEntity.ok(chat);
    }

    // DELETE - delete chat
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable String chatId) {
        chatService.deleteChatById(chatId);
        return ResponseEntity.ok("Chat deleted successfully");
    }

    // PATCH - rename chat_name
    @PatchMapping("/rename")
    public ResponseEntity<String> renameChat(@RequestBody RenameChatRequest requestData) {
        String response = chatService.renameChat(requestData.getChatId(), requestData.getUpdatedChatName());
        return ResponseEntity.ok(response);
    }


}
