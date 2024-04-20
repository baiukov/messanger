package me.chat.chatclient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

public class ChatApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a WebView
        WebView webView = new WebView();

        // Load HTML content
        //webView.getEngine().loadContent(getClass().getClassLoader().getResource("html/index.html").toString());

        // Add the WebView to a layout
        VBox root = new VBox(webView);

        // Create the scene
        Scene scene = new Scene(root, 600, 400);

        // Set the scene to the stage and show
        primaryStage.setScene(scene);
        primaryStage.setTitle("HTML Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}