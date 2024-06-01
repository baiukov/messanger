package me.chat.chatclient;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.Objects;

import static javafx.application.Application.launch;

/**
 * Toto je aplikace využívající JavaFX pro spuštění interního prohlížeče v applikaci.
 * Používá se jako hlávní trída pro spuštění deskotopové aplikace. Je nutné
 *
 * @author Aleksei Baiukov
 * @version 02.05
 */

public class Main {
    public static void main(String[] args) {
        launch(Client.class, args);
    }

    public static class Client extends Application {

        /**
         * Uložení loggeru
         */

        public Client() {}

        /**
         * Metoda pro nastartování desktopové aplikace, spustí ji na předané primární scnéně
         *
         * @param primaryStage primární scéna
         */
        @Override
        public void start(Stage primaryStage) {

            // inicializace prohlížeče
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            File htmlFile = new File(Objects.requireNonNull(
                    getClass().getResource("/html/pages/login/index.html")).getFile()
            );

            webEngine.load("http://45.67.57.171:8001/pages/login/");

            // připojení k serveru
            SocketClient socketClient = new SocketClient();
            socketClient.start("45.67.57.171");

            MessageRouter messageRouter = new MessageRouter(webEngine, socketClient);
            socketClient.setMessageRouter(messageRouter);

            // přiřázení JS Obejktu k frontendu pro komunikaci
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

        /**
         * Hlavní metoda aplikace pro spuštění. Vyvolá konstruktor hlávního okna, který pak
         * vytvoří i prohlížeč a připojí se k socketovému serveru
         *
         * @param args argumenty zadány při spuštění applikace
         */
    }
}