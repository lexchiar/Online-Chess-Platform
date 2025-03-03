package com.tco.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;

public class CredentialTest {

    @Test
    @DisplayName("ahuss12")
    public void testUrlWithTunnelEnabled() throws Exception {
        setEnvironmentVariable("CS314_USE_DATABASE_TUNNEL", "true");
        String expectedUrl = "jdbc:mariadb://faure.cs.colostate.edu/cs414_team15";
        String actualUrl = Credential.url();
        assertEquals(expectedUrl, actualUrl);
        resetEnvironmentVariables();
    }

    @Test
    @DisplayName("ahuss12")
    public void testUrlWithTunnelDisabled() throws Exception {
        setEnvironmentVariable("CS314_USE_DATABASE_TUNNEL", "false");
        String expectedUrl = "jdbc:mariadb://faure.cs.colostate.edu/cs414_team15";
        String actualUrl = Credential.url();
        assertEquals(expectedUrl, actualUrl);
        resetEnvironmentVariables();
    }

    @Test
    @DisplayName("ahuss12")
    public void testUrlWithTunnelUnset() throws Exception {
        removeEnvironmentVariable("CS314_USE_DATABASE_TUNNEL");
        String expectedUrl = "jdbc:mariadb://faure.cs.colostate.edu/cs414_team15";
        String actualUrl = Credential.url();
        assertEquals(expectedUrl, actualUrl);
    }

    // Helper methods to set and reset environment variables
    private static final Map<String, String> originalEnv = System.getenv();

    private void setEnvironmentVariable(String key, String value) throws Exception {
        modifyEnvironmentVariable(key, value);
    }

    private void removeEnvironmentVariable(String key) throws Exception {
        modifyEnvironmentVariable(key, null);
    }

    private void resetEnvironmentVariables() throws Exception {
        for (String key : System.getenv().keySet()) {
            if (!originalEnv.containsKey(key)) {
                removeEnvironmentVariable(key);
            } else {
                setEnvironmentVariable(key, originalEnv.get(key));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @DisplayName("ahuss12")
    private void modifyEnvironmentVariable(String key, String value) throws Exception {
        Map<String, String> env = System.getenv();
        Class<?> envClass = env.getClass();
        Field field = envClass.getDeclaredField("m");
        field.setAccessible(true);
        Map<String, String> writableEnv = (Map<String, String>) field.get(env);
        if (value == null) {
            writableEnv.remove(key);
        } else {
            writableEnv.put(key, value);
        }
    }
}