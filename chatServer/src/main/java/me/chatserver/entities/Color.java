package me.chatserver.entities;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.List;

/**
 * Třída popisující barvu. Ukládá a zpracová její data
 * a aktuální stavy dat
 *
 * @author Aleksei Baiukov
 * @version 01.05.2024
 */
@Entity
@Table(name = "colors")
public class Color {

    /**
     * Identifikáční číslo barvy
     */
    @Id
    private int id;

    /**
     * Název barvy
     */
    @Column(name = "name")
    private String name;

    /**
     * Hex kód barvy
     */
    @Column(name = "hexcode")
    private String hexcode;

    /**
     * Uživatelé, které mají tuto barvu v avataru
     */
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

    /**
     * Konstruktor třídy
     */
    public Color() { }

    /**
     * Konstruktor třídy specifikující hex kód barvy
     */
    public Color(String hexcode) {
        this.hexcode = hexcode;
    }

    // Gettery
    public int getID() { return id; }

    public String getName() { return name; }

    public String getHexcode() { return hexcode; }

    // Settery
    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setHexcode(String hexcode) { this.hexcode = hexcode; }

    /**
     * Metoda pro převedení instance k textovému řádku
     *
     * @return textová reprezentace barvy
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "ID: {0}, name: {1}, hexcode: {2}",
                id,
                name,
                hexcode
        );
    }

    /**
     * Metoda pro porovnání vztahů. Vrátí True, pokud barvy mají stejné identifikační číslo, název a hex kód
     *
     * @param color entita barvy k porovnání
     * @return jestli je tatéž
     */
    public boolean equals(Color color) {
        return id == color.getID() && name.equals(color.getName()) && hexcode.equals(color.getHexcode());
    }
}
