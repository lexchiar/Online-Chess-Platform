package com.tco.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;

public class DateConverterTest {

    @Test
    @DisplayName("ahuss12")
    public void testLocalDateTimeToSQLString() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 28, 15, 37, 40);
        String expected = "'2024-10-28 15:37:40'";
        String result = DateConverter.LocalDateTimeToSQLString(date);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("ahuss12")
    public void testLocalDateTimeToSQLStringSingleDigitValues() {
        LocalDateTime date = LocalDateTime.of(2023, 1, 2, 3, 4, 5);
        String expected = "'2023-1-2 3:4:5'";
        String result = DateConverter.LocalDateTimeToSQLString(date);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("ahuss12")
    public void testLocalDateTimeToSQLStringNullInput() {
        LocalDateTime date = null;
        assertThrows(NullPointerException.class, () -> {
            DateConverter.LocalDateTimeToSQLString(date);
        });
    }
}

