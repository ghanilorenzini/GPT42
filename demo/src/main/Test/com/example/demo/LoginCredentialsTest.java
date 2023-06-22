package com.example.demo;

import com.example.demo.LoginCredentials;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginCredentialsTest {

    @Test
    public void testIsValid_ValidCredentials_ReturnsTrue() {
        // Arrange
        String validUsername = "test";
        String validPassword = "test";

        // Act
        LoginCredentials credentials = new LoginCredentials(validUsername, validPassword);
        boolean isValid = credentials.isValid();

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testIsValid_EmptyUsername_ReturnsFalse() {
        // Arrange
        String emptyUsername = "";
        String validPassword = "pa$$w0rd";

        // Act
        LoginCredentials credentials = new LoginCredentials(emptyUsername, validPassword);
        boolean isValid = credentials.isValid();

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testIsValid_EmptyPassword_ReturnsFalse() {
        // Arrange
        String validUsername = "john_doe";
        String emptyPassword = "";

        // Act
        LoginCredentials credentials = new LoginCredentials(validUsername, emptyPassword);
        boolean isValid = credentials.isValid();

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testIsValid_EmptyCredentials_ReturnsFalse() {
        // Arrange
        String emptyUsername = "";
        String emptyPassword = "";

        // Act
        LoginCredentials credentials = new LoginCredentials(emptyUsername, emptyPassword);
        boolean isValid = credentials.isValid();

        // Assert
        assertFalse(isValid);
    }
}
