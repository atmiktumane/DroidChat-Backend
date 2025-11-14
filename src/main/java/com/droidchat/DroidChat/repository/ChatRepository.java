package com.droidchat.DroidChat.repository;

import com.droidchat.DroidChat.model.ChatModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatModel, String> {
    List<ChatModel> findByUserId(String userId);
}
