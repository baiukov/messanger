package me.chat.chatclient;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

import java.io.File;

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
        });
    }

    public void receiveMessage(String message) {
        socketClient.sendMessage(message);
    }

    public void getID() {
        Platform.runLater(() -> {
            engine.executeScript("window.sendID('" + id + "')");
        });
    }

    public void setID(String id) {
        System.out.println(id);
        this.id = id;
    }

    public void goToMain() {
        File htmlFile = new File(getClass().getResource("/html/pages/main/index.html").getFile());
        engine.load(htmlFile.toURI().toString());
    }
}
