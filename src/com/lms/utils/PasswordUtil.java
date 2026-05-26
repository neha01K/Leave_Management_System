package com.lms.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm unavailable", exception);
        }
    }

    public static boolean matches(String rawPassword, String passwordHash) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            return false;
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            return false;
        }
        return hashPassword(rawPassword).equals(passwordHash);
    }
}
