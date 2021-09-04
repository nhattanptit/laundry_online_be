package com.laundy.laundrybackend.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static boolean checkIfCurrentUserMatchUsername(String username){
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(username);
    }
}
