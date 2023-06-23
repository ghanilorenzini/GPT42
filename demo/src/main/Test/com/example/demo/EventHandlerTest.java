package com.example.demo;

import com.example.demo.EventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventHandlerTest {
    @Test
    public void testIsDarkMode_WhenDarkModeIsSet_ReturnsTrue() {
        // Arrange
        EventHandler eventHandler = new EventHandler();
        eventHandler.setDarkMode(true);

        // Act
        boolean isDarkMode = eventHandler.isDarkMode();

        // Assert
        Assertions.assertTrue(isDarkMode);
    }

    @Test
    public void testIsDarkMode_WhenDarkModeIsNotSet_ReturnsFalse() {
        // Arrange
        EventHandler eventHandler = new EventHandler();
        eventHandler.setDarkMode(false);

        // Act
        boolean isDarkMode = eventHandler.isDarkMode();

        // Assert
        Assertions.assertFalse(isDarkMode);
    }
}
