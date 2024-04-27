package me.chat.chatclient;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.function.Function;

public class MessageRouter {

    private final WebEngine engine;

    private final SocketClient socketClient;

    private String id = null;

    public MessageRouter(WebEngine webEngine, SocketClient sc) {
        engine = webEngine;
        socketClient = sc;
    }

    public void sendMessageToFront(String message) {
        System.out.println("toFront " + message);
        Platform.runLater(() -> {
            engine.executeScript("window.sendDataToFront('" + message + "')");
        });
    }

    public void receiveMessage(String message) {
        System.out.println(message);
        socketClient.sendMessage(message);
    }

    public void getID() {
        System.out.println("toget " + id);
        Platform.runLater(() -> {
            engine.executeScript("window.sendID('" + id + "')");
        });
    }

    public void setID(String id) {
        System.out.println(id);
        this.id = id;
    }

    public void goToDialogue(String userID) {
        switchPage("dialogue", "window.sendDataToFront('SETDIALOGUEWITH " + userID + "')");
    }

    public void switchPage(String folder, String commandOnReady) {
        File htmlFile = new File(getClass().getResource("/html/pages/" + folder + "/index.html").getFile());
        engine.load(htmlFile.toURI().toString());
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                engine.executeScript("window.sendID('" + id + "')");
                if (commandOnReady == null) return;
                engine.executeScript(commandOnReady);
            }
        });
    }
}
