package me.chatserver.controllers;

import me.chatserver.controllers.commands.CommandController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Třída pro posloucháni a komunikaci s klienty.
 *
 * @author Aleksei Baiukov
 * @version 30.03.2024
 */
public class ClientHandler extends Thread {
    // uložení klienta
    private final Socket clientSocket;

    // uložení streamu pro posílání zpráv
    private PrintWriter out;

    private Map<String, Socket> clients = new HashMap<>();

    private CommandController commandController = new CommandController(this);

    // uložení kontrolleru komand
    /**
     * Konstruktor třídy specifikující socket klienta
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Metoda pro nastartování posloucháče klientu, který pokusí se vytvořit streamy pro
     * posílání a načítání zpráv, a když dostane zprávu od klienta, přepošle ji kontrolleru
     * komand a bude čekat dál, pokud se žádná chyba nenastane
     */
    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            this.out = out;
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                commandController.onCommand(inputLine);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Metoda pro posílání zpráv klientovi přes uložený stream na posílání.
     *
     * @param message zpráva pro klienta
     */
    public void sendMessage(String message) {
        this.out.println(message);
    }
}
