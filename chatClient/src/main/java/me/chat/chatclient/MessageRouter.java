package me.chat.chatclient;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

/**
 * Třída pro přesměrování zpráv a komunikaci s klientem, tzv. most
 * Pokud dostane od frontendu zprávu, tak ji přepošle na serveru, pokud zpráva
 * přijde ze serveru hned je odeslána frontendu
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
public class MessageRouter {

    /**
     * Uložení webového motoru pro komunikaci s frontendem
     */
    private final WebEngine engine;

    /**
     * Uložení socketu klienta
     */
    private final SocketClient socketClient;

    /**
     * Uložení identifikačního čísla lokálního uživatele
     */
    private String id = null;

    /**
     * Uložení loggeru
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Konstruktor specifikující webový motor a soket klienta
     *
     * @param webEngine webový moto
     * @param  sc socket klienta
     */
    public MessageRouter(WebEngine webEngine, SocketClient sc) {
        engine = webEngine;
        socketClient = sc;
    }

    /**
     * Metoda pro odeslání zprávy frontendu. Vyvolá JS metodu a předá jí data
     *
     * @param message zpráva
     */
    public void sendMessageToFront(final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                engine.executeScript("window.sendDataToFront('" + message + "')");
                log.info("Message sent to front - " + message);
            }
        });
    }

    /**
     * Metoda pro získání dat z frontendu. Vyvolá se z JS aplikace a budou jí předana data
     *
     * @param message zpráva
     */
    public void receiveMessage(String message) {
        if (message.startsWith("LOG") && !message.startsWith("LOGIN")) {
            log.info(message);
            return;
        }
        socketClient.sendMessage(message);
        log.info("Message sent to server: " + message);
    }

    /**
     * Metoda pro přeposlání uloženého identifikačního čísla
     */
    public void getID() {
        Platform.runLater(new Runnable() {
            public void run() {
                engine.executeScript("window.sendID('" + id + "')");
                log.info("ID send to front: " + id);
            }
        });
    }

    /**
     * Metoda pro nastavení uloženého identifikačního čísla
     *
     * @param id identifikační číslo
     */
    public void setID(String id) {
        log.info("Set id " + id);
        this.id = id;
    }

    /**
     * Metoda pro přesměrování na stránku chatů
     *
     * @param userID identifikační číslo uživatele
     */
    public void goToDialogue(String userID) {
        switchPage("dialogue", "window.sendDataToFront('SETDIALOGUEWITH " + userID + "')");
        log.info("User has been redirected to dialogue with user " + userID);
    }

    /**
     * Metoda pro přesměrování na kteroukoliv stránku podle její názvu.
     * Po načtení bude spuštěn JS příkaz
     *
     * @param folder název složky se stránkou
     * @param commandOnReady příkaz vyvolaný po načtení
     */
    public void switchPage(final String folder, final String commandOnReady) {
        File htmlFile = new File(getClass().getResource("/html/pages/" + folder + "/index.html").getFile());
        engine.load(htmlFile.toURI().toString());
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
                                Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    engine.executeScript("window.sendID('" + id + "')");
                    if (commandOnReady == null) return;
                    engine.executeScript(commandOnReady);
                }
                log.info("Page has been successfully switch to " + folder);
            }
        });
    }
}
