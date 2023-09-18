package com.abuldovi.backauthproject.controllers;

import com.abuldovi.backauthproject.config.UserAuthProvider;
import com.abuldovi.backauthproject.dto.CredentialsDTO;
import com.abuldovi.backauthproject.dto.SignUpDTO;
import com.abuldovi.backauthproject.dto.UserDTO;
import com.abuldovi.backauthproject.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDTO credentialsDTO){
        UserDTO user = userService.login(credentialsDTO);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO signUpDTO){
        UserDTO user = userService.register(signUpDTO);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }
}
