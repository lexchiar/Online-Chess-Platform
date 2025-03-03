package com.tco.gamemanagement;

import java.time.LocalDateTime;

public class Invitation {
    private int invitationId;
    private int senderId;  //userobject?
    private int receiverId;  // again userobject?
    private int matchId;
    private InvitationStatus status;
    private LocalDateTime timestamp;

    public Invitation(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = InvitationStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public int getInvitationId() { return invitationId; }
    public void setInvitationId(int invitationId) { this.invitationId = invitationId; }
    public int getSenderId() { return senderId; }
    public int getReceiverId() { return receiverId; }
    public int getMatchId() { return matchId; }
    public InvitationStatus getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void accept() {
        this.status = InvitationStatus.ACCEPTED;
    }

    public void decline() {
        this.status = InvitationStatus.DECLINED;
    }

    public void cancel() {
        this.status = InvitationStatus.CANCELLED;
    }
}