package com.lms.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE = "db.properties";

    private final String url;
    private final String username;
    private final String password;

    private DatabaseConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseConfig load() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read " + CONFIG_FILE, exception);
        }

        String url = firstNonBlank(System.getenv("LMS_DB_URL"), properties.getProperty("db.url"));
        String username = firstNonBlank(System.getenv("LMS_DB_USERNAME"), properties.getProperty("db.username"));
        String password = firstNonBlank(System.getenv("LMS_DB_PASSWORD"), properties.getProperty("db.password"));

        if (isBlank(url) || isBlank(username) || isBlank(password)) {
            throw new IllegalStateException(
                    "Database config missing. Set LMS_DB_URL, LMS_DB_USERNAME, LMS_DB_PASSWORD or create src/main/resources/db.properties.");
        }

        return new DatabaseConfig(url, username, password);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private static String firstNonBlank(String first, String second) {
        return isBlank(first) ? second : first;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
