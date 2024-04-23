package me.chatserver;

import me.chatserver.controllers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            while (true) {
                Socket clientSocket = serverSocket.accept();

                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}