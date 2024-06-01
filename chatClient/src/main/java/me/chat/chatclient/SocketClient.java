package me.chat.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketClient {

    // uložení portu socketového serveru
    private static int SERVER_PORT = 8686;

    // uložení streamu pro posílání zpráv serveru
    private PrintWriter out;

    // uložení streamu pro získání zpráv od serveru
    private BufferedReader in;

    // uložení instance přesměrováče zpráv
    private MessageRouter messageRouter;

    private static final Logger log = LogManager.getLogger();

    // uložení instance logger
    //private static final Logger logger = LogManager.getLogger(SocketClient.class);

    /**
     * Konstruktor třídy specifikující přesměrováče zpráv a komunikace s frontendem
     */
    public void setMessageRouter(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    /**
     * Metoda pro nastartování socketu klienta, který se pokusí připojít k uvedenému serveru
     * na uvedeném portu, až se k němu přípojí, vytovoří nové vlakno, které se bude se serverem komunikovat.
     * Až se nějakou zprávu dostane přesměruje ji přes přesměrováč zpráv na frontend,
     * pokud se zádná chyba nenastane
     *
     * @throws IOException chyba vstupu vystupu
     */
    public void start(String ip) {
        try {
            Socket socket = new Socket(ip, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread readerThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        String serverResponse;
                        while ((serverResponse = in.readLine()) != null) {
                            log.info("Message received from the server - " + serverResponse);
                            if (messageRouter != null) {
                                messageRouter.sendMessageToFront(serverResponse);
                            }
                        }
                    } catch (IOException ex) {
                        log.error("Exception occurred at reader thread start " + ex.getMessage());
                    }
                }
            });
            readerThread.start();
            log.info("Client has been connected to server " + ip + ":" + SERVER_PORT);
        } catch (IOException ex) {
            log.error("Exception occurred at client socket start " + ex.getMessage());
        }
    }

    /**
     * Metoda pro posílání zpráv serveru. Přes steam out, který byl vytvořen při nastartování socket
     * přepošle zprávu dostanou od frontendu, resp. od přesměrováče hned n server
     *
     * @param message zpráva pro server
     */
    public void sendMessage(Object message) {
        if (out != null) {
            out.println(message);
            log.info("Message sent to server - " + message);
        }
    }
}
