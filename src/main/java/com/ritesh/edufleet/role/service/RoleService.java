package com.ritesh.edufleet.role.service;

import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.enums.UserRoleEnum;
import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.role.dto.RoleCreateRequestDto;
import com.ritesh.edufleet.role.entity.Role;
import com.ritesh.edufleet.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Function to get role by id
     *
     * @param id
     * @return
     */
    public Role getRoleById(long id) {
        return roleRepository.findById(id);
    }

    /**
     * Function to get role by name
     *
     * @param name
     * @return
     */
    public Role getRoleByName(String name) {
        UserRoleEnum userRole;
        try {
            userRole = UserRoleEnum.valueOf(name.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role found");
        }
        return roleRepository.findByName(userRole);
    }

    /**
     * Function to create new role
     *
     * @param dto
     * @return
     */
    public String create(RoleCreateRequestDto dto) {
        UserRoleEnum userRoleEnum;
        try {
            userRoleEnum = UserRoleEnum.valueOf(dto.getName().toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role name found");
        }

        Role existedRole = roleRepository.findByName(userRoleEnum);
        if (existedRole != null) {
            throw new BadRequestException("Role already exists");
        }

        Role role = new Role();
        role.setName(userRoleEnum);
        roleRepository.save(role);
        return "Role created";
    }

    /**
     * Function to get users for specific role name
     *
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(String name) {
        log.warn("What is the name>>>>>>>>>>>" + name);
        UserRoleEnum uRole;
        try {
            uRole = UserRoleEnum.valueOf(name.toUpperCase().trim());
            log.warn("What is the uRole>>>>>>>>>>>" + uRole);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role name");
        }

        Role role = roleRepository.findByName(uRole);
        log.warn("What role did i fetch?" + role.getName());
        return role.getUsers();
    }

    /**
     * Function to get listing of all roles
     *
     * @return
     */
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
