package com.droidchat.DroidChat.repository;

import com.droidchat.DroidChat.model.OtpModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OtpRepository extends MongoRepository<OtpModel, String> {

    // Job Scheduler - to delete expired OTPs after every 5 minutes
    List<OtpModel> findByCreationTimeBefore(LocalDateTime expriyTime);
}
