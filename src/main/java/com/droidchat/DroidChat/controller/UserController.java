package com.droidchat.DroidChat.controller;

import com.droidchat.DroidChat.dto.ResponseDTO;
import com.droidchat.DroidChat.dto.UserDTO;
import com.droidchat.DroidChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDto){
        return ResponseEntity.ok(userService.registerUser((userDto)));
    }
}
