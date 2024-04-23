package me.chat.chatclient;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

public class MessageRouter {

    private final WebEngine engine;

    private final SocketClient socketClient;


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
}
