package com.ebki.security;

public enum UserPermission {
    USER_RED("READ"),
    USER_WRITE("WRITE");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
