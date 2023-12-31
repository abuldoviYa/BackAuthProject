package com.abuldovi.backauthproject.mappers;

import com.abuldovi.backauthproject.dto.SignUpDTO;
import com.abuldovi.backauthproject.dto.UserDTO;
import com.abuldovi.backauthproject.entities.Role;
import com.abuldovi.backauthproject.entities.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@NoArgsConstructor
public class UserMapperImpl {
    public UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }

    public User signUpToUser(SignUpDTO signUpDTO){
        return User.builder()
                .firstName(signUpDTO.firstname())
                .lastName(signUpDTO.lastname())
                .login(signUpDTO.login())
                .build();
    }
}
