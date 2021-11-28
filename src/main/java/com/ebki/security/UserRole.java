package com.ebki.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ebki.security.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(USER_RED, USER_WRITE)),
    DRIVER(Sets.newHashSet(USER_RED, USER_WRITE));

    private final Set<UserPermission> userPermissions;

    UserRole(Set<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Set<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public Set<SimpleGrantedAuthority> grantedAuthorities() {
       Set<SimpleGrantedAuthority> permission =
               getUserPermissions()
                       .stream()
                       .map(userPermissions -> new SimpleGrantedAuthority(userPermissions.getPermission()))
                       .collect(Collectors.toSet());
       permission.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
       return permission;
    }
}
