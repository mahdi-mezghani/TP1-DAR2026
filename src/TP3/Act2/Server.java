package TP3.Act2;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 1235;

    // ressource critique partagée entre tous les threads ClientProcess
    private static int compteurOperations = 0;

    // accès synchronisé (équivalent à un bloc synchronized(this) sur la classe)
    public static synchronized int incrementerCompteur() {
        compteurOperations++;
        return compteurOperations;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server multi-thread (objets) démarré sur le port " + PORT);

            int numeroClient = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                numeroClient++;

                System.out.println("Client n°" + numeroClient + " connecté depuis "
                        + socket.getRemoteSocketAddress());

                Thread t = new Thread(new ClientProcess(socket, numeroClient));
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}