package me.chat.chatclient;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class MessageRouter {

    private final WebEngine engine;

    private final SocketClient socketClient;

    private String id = null;

    public MessageRouter(WebEngine webEngine, SocketClient sc) {
        engine = webEngine;
        socketClient = sc;
    }

    public void sendMessageToFront(String message) {
        Platform.runLater(() -> {
            engine.executeScript("window.sendDataToFront('" + message + "')");
            log.info("Message sent to front - " + message);
        });
    }

    public void receiveMessage(String message) {
        if (message.startsWith("LOG")) {
            log.info(message);
            return;
        }
        socketClient.sendMessage(message);
        log.info("Message sent to server: " + message);
    }

    public void getID() {
        Platform.runLater(() -> {
            engine.executeScript("window.sendID('" + id + "')");
            log.info("ID send to front: " + id);
        });
    }

    public void setID(String id) {
        log.info("Set id " + id);
        this.id = id;
    }

    public void goToDialogue(String userID) {
        switchPage("dialogue", "window.sendDataToFront('SETDIALOGUEWITH " + userID + "')");
        log.info("User has been redirected to dialogue with user " + userID);
    }

    public void switchPage(String folder, String commandOnReady) {
        File htmlFile = new File(Objects.requireNonNull(
                getClass().getResource("/html/pages/" + folder + "/index.html")).getFile()
        );
        engine.load(htmlFile.toURI().toString());
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                engine.executeScript("window.sendID('" + id + "')");
                if (commandOnReady == null) return;
                engine.executeScript(commandOnReady);
            }
            log.info("Page has been successfully switch to " + folder);
        });
    }
}
