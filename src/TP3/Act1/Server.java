package TP3.Act1;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final int PORT = 1234;
    // numéro d'ordre de connexion (accédé uniquement depuis le thread principal)
    private static final AtomicInteger compteurClients = new AtomicInteger(0);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server multi-thread démarré sur le port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                int numero = compteurClients.incrementAndGet();

                System.out.println("Client n°" + numero + " connecté depuis "
                        + socket.getRemoteSocketAddress());

                // un thread dédié par client
                Thread t = new Thread(new ClientProcess(socket, numero));
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}
