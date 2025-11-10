package com.droidchat.DroidChat.service;

import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;

public interface UserService {
    ResponseDTO registerUser(UserDTO userDto);
}
