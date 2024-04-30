package me.chatserver.entities;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "sender")
    private List<Message> sendMessages;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "receiver")
    private List<Message> receivedMessages;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color", referencedColumnName = "id")
    private Color color;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public String getID() { return this.id; }
    public String getUserName() { return this.userName; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getPassword() { return this.password; }
    public Color getColor() { return this.color; }
    public List<Message> getSentMessages() { return this.sendMessages; }
    public List<Message> getReceivedMessages() { return this.receivedMessages; }

    public void setID(UUID id) { this.id = id.toString(); }
    public void setUserName(String userName) { this.userName = userName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String secondName) { this.lastName = secondName; }
    public void setPassword(String password) { this.password = password; }
    public void setColor(Color color) { this.color = color; }
    public void setSentMessages(List<Message> messages) { this.sendMessages = messages; }
    public void setReceivedMessages(List<Message> messages) { this.receivedMessages = messages; }

    public boolean equals(User user) {
        return this.userName.equals(user.getUserName());
    }

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
