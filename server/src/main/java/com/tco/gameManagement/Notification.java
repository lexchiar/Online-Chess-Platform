package com.tco.gamemanagement;

import java.time.LocalDateTime;

public class Notification {
    private int userId;  // UserObject?
    private String message;
    private int notificationId;
    private boolean isRead;
    private LocalDateTime timestamp;
    // private NotificationType type; // Optional


    public Notification(int userId, String message, int notificationId, boolean isRead, LocalDateTime timestamp) {
        this.userId = userId;
        this.message = message;
        this.notificationId = notificationId;
        this.isRead = isRead;
        this.timestamp = timestamp;
    }

    public Notification createNotification() {
        // method implementation here

        return new Notification(userId, message, notificationId, isRead, timestamp);
    }

    public boolean sendNotification() {
        // method implementation here

        return true;
    }

    public boolean markAsRead() {
        // method implementation here

        return isRead;
    }

    public void deleteNotification() {
        // method implementation here
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
    public boolean getIsRead() { return isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}