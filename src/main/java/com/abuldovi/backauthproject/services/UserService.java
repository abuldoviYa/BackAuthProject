package com.abuldovi.backauthproject.services;

import com.abuldovi.backauthproject.dto.CredentialsDTO;
import com.abuldovi.backauthproject.dto.SignUpDTO;
import com.abuldovi.backauthproject.dto.UserDTO;
import com.abuldovi.backauthproject.entities.Role;
import com.abuldovi.backauthproject.entities.User;
import com.abuldovi.backauthproject.exceptions.AppException;
import com.abuldovi.backauthproject.mappers.UserMapperImpl;
import com.abuldovi.backauthproject.repo.RoleRepository;
import com.abuldovi.backauthproject.repo.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapperImpl userMapperImpl;
    private final RoleRepository roleRepository;

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
        user.setRole(Set.of(Role.builder().roleName("user").build()));
        User savedUser = userRepository.save(user);
        return userMapperImpl.toUserDTO(savedUser);
    }

    public UserDTO findByUserName(String username){
        Optional<User> oUser = userRepository.findUserByLogin(username);
        if(oUser.isEmpty()){
            throw new AppException("User has not been found", HttpStatus.BAD_REQUEST);
        }
        return userMapperImpl.toUserDTO(oUser.get());
    }


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("admin");
        adminRole.setRoleTitle("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("user");
        userRole.setRoleTitle("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

        User adminUser = new User();
        adminUser.setRole((Set.of(Role.builder().roleName("admin").build())));
        adminUser.setPassword(passwordEncoder.encode(CharBuffer.wrap("admin")));
        adminUser.setLogin("admin");
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        userRepository.save(adminUser);

        User testUser = new User();
        testUser.setRole((Set.of(Role.builder().roleName("user").build())));
        testUser.setPassword(passwordEncoder.encode(CharBuffer.wrap("test")));
        testUser.setLogin("test");
        testUser.setFirstName("test");
        testUser.setLastName("test");
        userRepository.save(testUser);

    }
}
