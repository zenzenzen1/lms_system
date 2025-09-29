package com.example.util;


public class RoleUtil {
    public static boolean hasRole(String roles, String roleToCheck) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        String[] roleArray = roles.split(" ");
        for (String role : roleArray) {
            if (role.trim().equalsIgnoreCase(roleToCheck)) {
                return true;
            }
        }
        return false;
    }
}
