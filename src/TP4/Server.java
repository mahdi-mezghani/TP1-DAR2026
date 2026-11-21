package TP4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/** UDP chat server. A client becomes known after sending its first message. */
public class Server {
    private static final int PORT = 1234;
    private static final int MAX_DATAGRAM_SIZE = 4096;

    public static void main(String[] args) {
        Set<SocketAddress> clients = new HashSet<SocketAddress>();

        try (DatagramSocket socket = new DatagramSocket(null)) {
            socket.bind(new InetSocketAddress(PORT));
            System.out.println("Serveur de chat UDP en écoute sur le port " + PORT);

            while (true) {
                byte[] buffer = new byte[MAX_DATAGRAM_SIZE];
                DatagramPacket received = new DatagramPacket(buffer, buffer.length);
                socket.receive(received);

                SocketAddress sender = received.getSocketAddress();
                String message = new String(received.getData(), received.getOffset(),
                        received.getLength(), StandardCharsets.UTF_8);
                System.out.println("[" + sender + "] " + message);

                if ("/quit".equalsIgnoreCase(message.trim())) {
                    if (clients.remove(sender)) {
                        System.out.println("Client déconnecté : " + sender
                                + " (" + clients.size() + " client(s) restant(s))");
                    }
                    continue;
                }

                if (clients.add(sender)) {
                    System.out.println("Client enregistré : " + sender
                            + " (" + clients.size() + " client(s))");
                }

                byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
                for (SocketAddress client : clients) {
                    if (!client.equals(sender)) {
                        DatagramPacket broadcast = new DatagramPacket(
                                messageBytes, messageBytes.length, client);
                        socket.send(broadcast);
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Impossible d'ouvrir le port " + PORT + " : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur de communication UDP : " + e.getMessage());
        }
    }
}
