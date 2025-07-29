package com.ritesh.edufleet.role.controller;

import com.ritesh.edufleet.role.dto.RoleCreateRequestDto;
import com.ritesh.edufleet.role.dto.UserListResponseDto;
import com.ritesh.edufleet.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {
    private final RoleService roleService;

    /**
     * Api to create a new role
     *
     * @param dto
     * @return
     */
    @PostMapping
    public String create(@Valid @RequestBody RoleCreateRequestDto dto) {
        return roleService.create(dto);

    }

    /**
     * Api to get users list for give role
     *
     * @param name
     * @return
     */
//    @SkipApiResponseWrapping
    @GetMapping("/{name}")
    public List<UserListResponseDto> getUsersByRole(@PathVariable String name) {
        return roleService.getUsersByRole(name)
                .stream()
                .map(UserListResponseDto::new)
                .toList();
    }
}
