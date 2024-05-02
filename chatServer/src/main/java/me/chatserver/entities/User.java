package me.chatserver.entities;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * Třída popisující entitu uživatele. Ukládá a zpracová jeho data
 * a aktuální stavy dat
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Entity
@EntityScan
@Table(name="actual_users")
public class User {

    /**
     * Identifikáční číslo uživatele
     */
    @Id
    private String id;

    /**
     * Uživatelské jméno
     */
    @Column(name = "user_name")
    private String userName;

    /*
     * Jméno uživatele
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Křestní jméno uživatele
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Heslo uživatele
     */
    @Column(name = "password")
    private String password;

    /**
     * Poslané zprávy uživatelem
     */
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "sender")
    private List<Message> sendMessages;

    /**
     * Získané zprávy uživatelem
     */
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "receiver")
    private List<Message> receivedMessages;

    /**
     * Barva uživatelského avataru
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color", referencedColumnName = "id")
    private Color color;

    /**
     * Konstruktor uživatele nastavující identifikační číslo
     */
    public User() {
        this.id = UUID.randomUUID().toString();
    }

    // GETTERY
    public String getID() { return this.id; }
    public String getUserName() { return this.userName; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getPassword() { return this.password; }
    public Color getColor() { return this.color; }
    public List<Message> getSentMessages() { return this.sendMessages; }
    public List<Message> getReceivedMessages() { return this.receivedMessages; }

    // SETTERY
    public void setID(UUID id) { this.id = id.toString(); }
    public void setUserName(String userName) { this.userName = userName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String secondName) { this.lastName = secondName; }
    public void setPassword(String password) { this.password = password; }
    public void setColor(Color color) { this.color = color; }
    public void setSentMessages(List<Message> messages) { this.sendMessages = messages; }
    public void setReceivedMessages(List<Message> messages) { this.receivedMessages = messages; }

    /**
     * Metoda pro porovnání uživatelů, vrátí True, pokud uživatele mají stejné uživatelské jméno
     *
     * @param user entita uživatele k porovnání
     * @return jestli je tentýž
     */
    public boolean equals(User user) {
        return this.userName.equals(user.getUserName());
    }

    /**
     * Metoda pro převedení instance k textovému řádku
     *
     * @return textová reprezentace uživatele
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "ID: {0}, userName: {1}, first name: {2}, last name: {3}, color: {4}",
                id,
                userName,
                firstName,
                lastName,
                color.getName()
        );
    }

}
