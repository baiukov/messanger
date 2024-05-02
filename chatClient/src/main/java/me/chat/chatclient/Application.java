package me.chat.chatclient;

import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

public class Application extends javafx.application.Application {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        File htmlFile = new File(Objects.requireNonNull(
                getClass().getResource("/html/pages/login/index.html")).getFile()
        );

        webEngine.load(htmlFile.toURI().toString());

        SocketClient socketClient = new SocketClient();
        socketClient.start("45.67.57.171");

        MessageRouter messageRouter = new MessageRouter(webEngine, socketClient);
        socketClient.setMessageRouter(messageRouter);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", messageRouter);
                log.info("Java connector has been attached to the front project as window member");
            }
        });

        StackPane root = new StackPane(webView);

        Scene scene = new Scene(root, 600, 900);

        log.info("Application has been started");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}