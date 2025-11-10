package com.droidchat.DroidChat.service.Impl;

import com.droidchat.DroidChat.dto.UserDTO;
import com.droidchat.DroidChat.model.UserModel;
import com.droidchat.DroidChat.repository.UserRepository;
import com.droidchat.DroidChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDto){
        UserModel user = userDto.convertToUserModel();

        UserModel savedUser = userRepository.save(user);

        return savedUser.convertToUserDTO();
    }
}
