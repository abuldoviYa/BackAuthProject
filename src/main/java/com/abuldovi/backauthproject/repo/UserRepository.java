package com.abuldovi.backauthproject.repo;

import com.abuldovi.backauthproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
}
