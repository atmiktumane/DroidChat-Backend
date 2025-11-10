package com.droidchat.DroidChat.dto;

import com.droidchat.DroidChat.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;

    public UserModel convertToUserModel(){
        return new UserModel(this.id, this.name, this.email, this.password);
    }
}
