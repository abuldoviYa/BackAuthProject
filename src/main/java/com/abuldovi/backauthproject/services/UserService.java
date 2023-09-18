package com.abuldovi.backauthproject.services;

import com.abuldovi.backauthproject.dto.CredentialsDTO;
import com.abuldovi.backauthproject.dto.SignUpDTO;
import com.abuldovi.backauthproject.dto.UserDTO;
import com.abuldovi.backauthproject.entities.User;
import com.abuldovi.backauthproject.exceptions.AppException;
import com.abuldovi.backauthproject.mappers.UserMapperImpl;
import com.abuldovi.backauthproject.repo.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapperImpl;

    public UserDTO login(CredentialsDTO credentialsDTO) {
        User user = userRepository.findUserByLogin(credentialsDTO.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDTO.password()), user.getPassword())) {
            return userMapperImpl.toUserDTO(user);
        }
        throw new AppException("Invaild password", HttpStatus.BAD_REQUEST);

    }

    public UserDTO register(SignUpDTO signUpDTO){
        Optional<User> oUser = userRepository.findUserByLogin(signUpDTO.login());
        if(oUser.isPresent()){
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        User user = userMapperImpl.signUpToUser(signUpDTO);
        System.out.println(signUpDTO.password());
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.password())));
        User savedUser = userRepository.save(user);
        return userMapperImpl.toUserDTO(savedUser);
    }
}
