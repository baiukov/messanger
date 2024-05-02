package me.chatserver;

import lombok.extern.slf4j.Slf4j;
import me.chatserver.controllers.ClientHandler;
import me.chatserver.services.SQLTemplateService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Třída pro spuštění soketového serveru, připojování klientů a nastavení komunikací s nimi.
 *
 * @author Aleksei Baiukov
 * @version 02.05.2024
 */
@Slf4j
public class Main {
    /**
     * Metoda pro nastartování aplikaci, resp. serveru, který bude spuštěn
     * na uvedeném portu a bude čekat na připojení klientů. Až se nějaký připojí
     * vytvoří pro něj nové vlakno, do kterého hodí nový posloucháč klientů, pokud se
     * žádná chyba nenastane
     *
     * @param args argumenty při spuštění této aplikaci v VM
     */
    public static void main(String[] args) {
        final int PORT = 8686;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Server successfully started. Listen on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
                log.info("Client has been connected to the server");
            }
        } catch (IOException e) {
            log.error("Error occured on server starting " + e.getMessage());
        }
    }

}