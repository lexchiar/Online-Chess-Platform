package com.tco.server;

import com.tco.requests.StatusCodeProvider;
import com.tco.misc.BadRequestException;
import com.tco.misc.JSONValidator;
import com.tco.requests.ConfigRequest;
import com.tco.requests.GuestRequest;
import com.tco.requests.LoginRequest; // Import LoginRequest
import com.tco.requests.SignupRequest; // Import SignupRequest
import com.tco.requests.Request;
import com.tco.requests.ValidateMoveRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import static spark.Spark.*;

public final class MicroServer {

    private static final Logger log = LoggerFactory.getLogger(MicroServer.class);
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_SERVER_ERROR = 500;

    MicroServer(int serverPort) {
        configureServer(serverPort);
        processRestfulAPIrequests();
    }

    /* Configure MicroServices Here. */

    private void processRestfulAPIrequests() {
        path("/api", () -> {
            before("/*", (req, res) -> logRequest(req));
            post("/config", (req, res) -> processHttpRequest(req, res, ConfigRequest.class));
            post("/guestMove", (req, res) -> processHttpRequest(req, res, GuestRequest.class));
            post("/validateMoves", (req, res) -> processHttpRequest(req, res, ValidateMoveRequest.class));
            post("/signup", (req, res) -> processHttpRequest(req, res, SignupRequest.class)); // Add signup endpoint
            post("/login", (req, res) -> processHttpRequest(req, res, LoginRequest.class)); // Add login endpoint
        });
    }

    /* everything below has been unchanged. */

    private String processHttpRequest(spark.Request httpRequest, spark.Response httpResponse, Type requestType) {
        setupResponse(httpResponse);
        String jsonString = httpRequest.body();
        try {
            JSONValidator.validate(jsonString, requestType);
            Request requestObj = new Gson().fromJson(jsonString, requestType);
            return buildJSONResponse(requestObj, httpResponse);
        } catch (IOException | BadRequestException e) {
            log.info("Bad Request - {}", e.getMessage());
            httpResponse.status(HTTP_BAD_REQUEST);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Server Error - ", e);
            httpResponse.status(HTTP_SERVER_ERROR);
            return gson.toJson(new ErrorResponse("Internal server error."));
        }
    }

    private void setupResponse(spark.Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        response.status(HTTP_OK);
    }

    private String buildJSONResponse(Request request, spark.Response httpResponse) throws BadRequestException {
        request.buildResponse();
        // Check if the request object has a getStatusCode method
        if (request instanceof StatusCodeProvider) {
            int statusCode = ((StatusCodeProvider) request).getStatusCode();
            httpResponse.status(statusCode);
        }
        String responseBody = new Gson().toJson(request);
        log.trace("Response - {}", responseBody);
        return responseBody;
    }

    private void logRequest(spark.Request request) {
        String message = "Request - "
                + "[" + dateTimeFormat.format(LocalDateTime.now()) + "] "
                + request.ip() + " "
                + "\"" + request.requestMethod() + " "
                + request.pathInfo() + " "
                + request.protocol() + "\" "
                + "[" + request.body() + "]";
        log.info(message);
    }

    private void configureServer(int serverPort) {
        port(serverPort);
        String keystoreFile = System.getenv("KEYSTORE_FILE");
        String keystorePassword = System.getenv("KEYSTORE_PASSWORD");
        if (keystoreProvided(keystoreFile, keystorePassword)) {
            secure(keystoreFile, keystorePassword, null, null);
            log.info("MicroServer running using HTTPS on port {}.", serverPort);
        } else {
            log.info("MicroServer running using HTTP on port {}.", serverPort);
        }

        // To Serve Static Files (SPA)

        staticFiles.location("/public/");
        redirect.get("/", "/index.html");
    }

    private boolean keystoreProvided(String keystoreFile, String keystorePassword) {
        return (keystoreFile != null && keystorePassword != null);
    }

    // Add a Gson instance
    private final Gson gson = new Gson();

    // Define an ErrorResponse class
    class ErrorResponse {
        String message;

        ErrorResponse(String message) {
            this.message = message;
        }
    }
}
