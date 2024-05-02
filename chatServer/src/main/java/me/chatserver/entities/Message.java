package me.chatserver.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Třída popisující zprávu uživatelů. Ukládá a zpracová její data
 * a aktuální stavy dat
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Entity
@Table(name = "text_messages")
public class Message {

    /**
     * Identifikáční číslo zprávy
     */
    @Id
    public String id;

    /**
     * Uživatel, který zprávu poslal
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    public User sender;

    /**
     * Uživatel, který zprávu přijal
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    public User receiver;

    /**
     * Obsah textové zprávy
     */
    @Column(name = "text")
    public String message;

    /**
     * Jestli už byla přijemcem přečtená
     */
    @Column(name = "is_read")
    public boolean isRead;

    /**
     * Datum a čas odeslání zprávy
     */
    @Column(name = "created_at")
    @Transient
    public Timestamp createdAt;

    /**
     * Konstruktor nastavující identifikační číslo zprávy
     */
    public Message() {
        this.id = UUID.randomUUID().toString();
    }

    // Gettery
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

    /**
     * Metoda pro převedení instance k textovému řádku
     *
     * @return textová reprezentace zprávy
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "ID: {0}, sender: {1}, receiver: {2}, message: {3}",
                id,
                sender,
                receiver,
                message
        );
    }
}
