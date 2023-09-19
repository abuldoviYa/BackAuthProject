package com.abuldovi.backauthproject.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
public class MessagesController {

    @GetMapping("/messages")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<List<String>> messages(){
        return ResponseEntity.ok(Arrays.asList("First", "Second"));
    }
}
