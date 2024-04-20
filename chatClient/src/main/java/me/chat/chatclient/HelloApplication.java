package me.chat.chatclient;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        File htmlFile = new File(getClass().getResource("/html/pages/login/index.html").getFile());

        webEngine.load(htmlFile.toURI().toString());

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                webEngine.executeScript("alert('123')");


//                JSObject window = (JSObject) webEngine.executeScript("window");
//                window.setMember("javaConsole", new JavaConsole());
//                webEngine.executeScript("console.log = function(message) { javaConsole.log(message); };");
//
//                try {
//                    Thread.sleep(10000);
//                    webEngine.executeScript("javaConnector.showAlert('Button clicked!');");
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                // Example of calling JavaScript function from Java
            }
        });

        StackPane root = new StackPane(webView);

        Scene scene = new Scene(root, 600, 900);

        primaryStage.setScene(scene);
        primaryStage.setTitle("HTML Viewer");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static class JavaConsole {
        public void log(String message) {
            // Print the message to the Java console
            System.out.println("JavaScript Console: " + message);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}