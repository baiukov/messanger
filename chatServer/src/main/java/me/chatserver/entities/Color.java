package me.chatserver.entities;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.List;

@Entity
@Table(name = "colors")
public class Color {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "hexcode")
    private String hexcode;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "id")
    private List<User> users;

    public Color() {

    }

    public Color(String hexcode) {
        this.hexcode = hexcode;
    }

    public int getID() { return id; }

    public String getName() { return name; }

    public String getHexcode() { return hexcode; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setHexcode(String hexcode) { this.hexcode = hexcode; }

    @Override
    public String toString() {
        return MessageFormat.format(
                "ID: {0}, name: {1}, hexcode: {2}",
                id,
                name,
                hexcode
        );
    }
}
