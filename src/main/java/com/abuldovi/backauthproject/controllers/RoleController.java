package com.abuldovi.backauthproject.controllers;

import com.abuldovi.backauthproject.entities.Role;
import com.abuldovi.backauthproject.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Role> createNewRole(@RequestBody Role role){
        Role savedRole = roleService.createNewRole(role);
        return ResponseEntity.ok(savedRole);
    }


    @PostMapping("/update")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Role> updateRole(@RequestBody Role role){
        Role savedRole = roleService.updateRole(role);
        return ResponseEntity.ok(savedRole);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Map<String, String> > delete(@RequestBody Role role){
        roleService.deleteRole(role);
        return ResponseEntity.ok(Map.of("message", "Role has been deleted"));
    }
}
