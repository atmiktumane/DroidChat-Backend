package com.droidchat.DroidChat.service.Impl;

import com.droidchat.DroidChat.dto.LoginDTO;
import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;
import com.droidchat.DroidChat.exception.EmailAlreadyExistsException;
import com.droidchat.DroidChat.exception.EmptyFieldException;
import com.droidchat.DroidChat.exception.InvalidCredentialsException;
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

        // Check if email is Empty
        if(userDto.getEmail().isEmpty()){
            throw new EmptyFieldException("Please provide email");
        }

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

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) {
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Invalid Email or Password"));

        // Match Password
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

//        System.out.println("User Data : "+ user.convertToUserDTO());

        UserDTO userDto = user.convertToUserDTO();

        userDto.setPassword("");

        // Prepare Login Response body
        return userDto;
    }
}
