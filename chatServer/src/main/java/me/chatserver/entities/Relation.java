package me.chatserver.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_relations")
public class Relation {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_1", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_2", referencedColumnName = "id")
    private User user2;

    @Column(name = "is_pinned")
    private boolean isPinned;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    public Relation() {
        this.id = UUID.randomUUID().toString();
        this.isBlocked = false;
        this.isPinned = false;
    }

    public String getId() { return id; }

    public User getUser1() { return user1; }

    public User getUser2() { return user2; }

    public boolean getIsPinned() { return isPinned; }

    public boolean isBlocked() { return isBlocked; }

    public void setId(String id) { this.id = id; }

    public void setUser1(User user) { this.user1 = user; }

    public void setUser2(User user) { this.user2 = user; }

    public void setIsPinned(boolean isPinned) { this.isPinned = isPinned; }

    public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }
}
