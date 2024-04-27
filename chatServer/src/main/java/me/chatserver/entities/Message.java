package me.chatserver.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "messages")
public class Message {

    @Id
    public String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    public User sender;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    public User receiver;

    @Column(name = "text")
    public String message;

    @Column(name = "is_read")
    public boolean isRead;

    @Column(name = "created_at")
    @Transient
    public Timestamp createdAt;

    public Message() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters

    public String getId() { return id; }

    public User getSender() { return sender; }

    public User getReceiver() { return receiver; }

    public String getMessage() { return message; }

    public boolean isRead() { return isRead; }

    public Timestamp getCreatedAt() { return createdAt; }

    // Settery

    public void setId(String id) { this.id = id; }

    public void setSender(User user) { this.sender = user; }

    public void setReceiver(User user) { this.receiver = user; }

    public void setMessage(String message) { this.message = message; }

    public void setIsRead(boolean isRead) { this.isRead = isRead; }

    public void setCreatedAt(Timestamp timestamp) { this.createdAt = timestamp; }
}
