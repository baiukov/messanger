package me.chat.chatclient;

import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.File;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        File htmlFile = new File(getClass().getResource("/html/pages/login/index.html").getFile());

        webEngine.load(htmlFile.toURI().toString());

        SocketClient socketClient = new SocketClient();
        socketClient.start("localhost");

        MessageRouter messageRouter = new MessageRouter(webEngine, socketClient);
        socketClient.setMessageRouter(messageRouter);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", messageRouter);
            }
        });

        StackPane root = new StackPane(webView);

        Scene scene = new Scene(root, 600, 900);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}