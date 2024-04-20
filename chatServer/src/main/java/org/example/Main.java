package org.example;

import org.example.entities.User;
import org.example.controllers.ClientHandler;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        test();

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

    private static void test() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("A");
        user.setPassword("123");
        user.setUserName("diogo");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            System.out.println(user.getID());
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        HibernateUtil.shutdown();
    }
}