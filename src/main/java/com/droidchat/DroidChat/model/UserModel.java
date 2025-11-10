package com.droidchat.DroidChat.model;

import com.droidchat.DroidChat.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserModel {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    public UserDTO convertToUserDTO(){
        return new UserDTO(this.id, this.name, this.email, this.password);
    }
}
