package me.chat.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import me.chat.chatclient.pages.LoginPage;

import java.io.IOException;

public class ChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        StackPane root = new StackPane();
//
//        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("JavaFX Application");
        stage.setScene(new LoginPage().getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}