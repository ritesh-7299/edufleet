package com.ritesh.edufleet.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.enums.UserRoleEnum;
import com.ritesh.edufleet.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum name;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isActive = true;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;
}
