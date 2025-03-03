package com.tco.requests;

import com.tco.database.UserDatabase;
import com.tco.misc.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequest extends Request implements StatusCodeProvider {

    private static final Logger log = LoggerFactory.getLogger(LoginRequest.class);

    private String username;
    private String password;
    private String message;
    private int statusCode = 200; // Default to OK

    @Override
    public void buildResponse() throws BadRequestException {
        if (username == null || password == null) {
            throw new BadRequestException("Username and password are required.");
        }

        try {
            UserDatabase userDb = new UserDatabase();
            boolean validCredentials = userDb.validateUser(username, password);
            if (validCredentials) {
                message = "Login successful.";
            } else {
                statusCode = 401; // Unauthorized
                message = "Invalid username or password.";
            }
        } catch (Exception e) {
            log.error("Error during login", e);
            throw new BadRequestException("Internal server error.");
        }
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMessage() { return message; }
}
