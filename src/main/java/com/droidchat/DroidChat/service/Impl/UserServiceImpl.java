package com.droidchat.DroidChat.service.Impl;

import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;
import com.droidchat.DroidChat.exception.EmailAlreadyExistsException;
import com.droidchat.DroidChat.model.UserModel;
import com.droidchat.DroidChat.repository.UserRepository;
import com.droidchat.DroidChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO registerUser(UserDTO userDto){
        // Check if Email already exists
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists : "+userDto.getEmail());
        }

        UserModel userModel = new UserModel();

        // Set Name & Email
        userModel.setName(userDto.getName());
        userModel.setEmail(userDto.getEmail());

        // Encrypt the password before saving
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save User data in DB
        UserModel savedUser = userRepository.save(userModel);

        return new ResponseDTO("User is registered successfully !");
    }
}
