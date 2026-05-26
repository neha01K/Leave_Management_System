package com.lms.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String rawPassword, String passwordHash) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            return false;
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, passwordHash);
    }
}
