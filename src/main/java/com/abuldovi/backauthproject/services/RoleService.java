package com.abuldovi.backauthproject.services;

import com.abuldovi.backauthproject.entities.Role;
import com.abuldovi.backauthproject.exceptions.AppException;
import com.abuldovi.backauthproject.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createNewRole(Role role){
        Optional<Role> foundRole = roleRepository.findRoleByRoleName(role.getRoleName());
        if(foundRole.isPresent()){
            throw new AppException("Role already exists", HttpStatus.BAD_REQUEST);
        }
        return roleRepository.save(role);
    }

    public Role updateRole(Role role){
        Optional<Role> foundRole = roleRepository.findRoleByRoleName(role.getRoleName());
        if(foundRole.isEmpty()){
            throw new AppException("There is no such role", HttpStatus.BAD_REQUEST);
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Role role){
        Optional<Role> foundRole = roleRepository.findRoleByRoleName(role.getRoleName());
        if(foundRole.isEmpty()){
            throw new AppException("Role already exists", HttpStatus.BAD_REQUEST);
        }
        try {
            roleRepository.delete(role);
        } catch (Exception e){
            System.out.println(e);
            throw new AppException("Role cannot be deleted as it has users connected", HttpStatus.FORBIDDEN);
        }


    }
}
