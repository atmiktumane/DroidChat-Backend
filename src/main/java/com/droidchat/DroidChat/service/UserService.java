package com.droidchat.DroidChat.service;

import com.droidchat.DroidChat.dto.LoginDTO;
import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;

public interface UserService {
    ResponseDTO registerUser(UserDTO userDto);

    UserDTO loginUser(LoginDTO loginDTO);

    ResponseDTO sendOtp(String email) throws Exception;
}
