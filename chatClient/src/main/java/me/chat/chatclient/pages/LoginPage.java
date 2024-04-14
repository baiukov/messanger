package me.chat.chatclient.pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class LoginPage {

    private Scene scene;

    public Scene getScene() { return scene; }

    public LoginPage() {

        StackPane root = new StackPane();

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        Label lblPassword = new Label("Password:");
        TextField txtPassword = new TextField();
        Button btnLogin = new Button("Login");


        root.getChildren().addAll(lblUsername, txtUsername, lblPassword, txtPassword, btnLogin);

        scene = new Scene(root, 800, 600);

    }
}
