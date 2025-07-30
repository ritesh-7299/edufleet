package com.ritesh.edufleet.role.repository;

import com.ritesh.edufleet.enums.UserRoleEnum;
import com.ritesh.edufleet.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);

    Role findByName(UserRoleEnum name);
}
