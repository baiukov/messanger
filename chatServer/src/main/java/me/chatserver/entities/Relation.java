package me.chatserver.entities;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * Třída popisující vztah uživatelů. Ukládá a zpracová jeho data
 * a aktuální stavy dat
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Entity
@Table(name = "user_relation")
public class Relation {

    /**
     * Identifikáční číslo vztahu
     */
    @Id
    private String id;

    /**
     * Uživatel, který inicioval vztah
     */
    @ManyToOne
    @JoinColumn(name = "user_1", referencedColumnName = "id")
    private User user1;

    /**
     * Druhý uživatel ve vztahu
     */
    @ManyToOne
    @JoinColumn(name = "user_2", referencedColumnName = "id")
    private User user2;

    /**
     * Jestli je přípnut chat odpovídající tomuto vztahu
     */
    @Column(name = "is_pinned")
    private boolean isPinned;

    /**
     * Jestli je vztah zablokován
     */
    @Column(name = "is_blocked")
    private boolean isBlocked;

    /**
     * Konstruktor třídy nastaví identifikační číslo, a označí vztah jako nezablokováný ani nepřípnutý
     */
    public Relation() {
        this.id = UUID.randomUUID().toString();
        this.isBlocked = false;
        this.isPinned = false;
    }

    // GETTERY
    public String getId() { return id; }

    public User getUser1() { return user1; }

    public User getUser2() { return user2; }

    public boolean getIsPinned() { return isPinned; }

    public boolean getIsBlocked() { return isBlocked; }

    // SETTERY
    public void setId(String id) { this.id = id; }

    public void setUser1(User user) { this.user1 = user; }

    public void setUser2(User user) { this.user2 = user; }

    public void setIsPinned(boolean isPinned) { this.isPinned = isPinned; }

    public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }

    /**
     * Metoda pro převedení instance k textovému řádku
     *
     * @return textová reprezentace vztahu
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "ID: {0}, user1: {1}, user2: {2}, isPinned: {3}, isBlocked: {4}",
                id,
                user1,
                user2,
                isPinned,
                isBlocked
        );
    }

    /**
     * Metoda pro porovnání vztahů, vrátí True, pokud vztahy mají stejné identifikační číslo
     *
     * @param relation entita vztahu k porovnání
     * @return jestli je tentýž
     */
    public boolean equals(Relation relation) {
        return id.equals(relation.getId());
    }
}
