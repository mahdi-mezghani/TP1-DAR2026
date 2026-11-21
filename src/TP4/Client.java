package TP4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Interactive UDP chat client with a separate thread for incoming messages. */
public class Client {
    private static final int SERVER_PORT = 1234;
    private static final int MAX_DATAGRAM_SIZE = 4096;

    public static void main(String[] args) {
        String serverHost = args.length > 0 ? args[0] : "localhost";

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in, "UTF-8")) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            InetSocketAddress server = new InetSocketAddress(serverAddress, SERVER_PORT);

            System.out.print("Nom d'utilisateur : ");
            String username = scanner.nextLine().trim();
            while (username.isEmpty()) {
                System.out.print("Le nom ne peut pas être vide. Nom d'utilisateur : ");
                username = scanner.nextLine().trim();
            }

            Thread receiver = new Thread(() -> receiveMessages(socket), "udp-chat-receiver");
            receiver.setDaemon(true);
            receiver.start();

            System.out.println("Chat connecté au serveur " + serverHost + ":" + SERVER_PORT);
            System.out.println("Saisissez un message (ou /quit pour quitter).");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("/quit".equalsIgnoreCase(line.trim())) {
                    send(socket, server, "/quit");
                    break;
                }
                if (!line.trim().isEmpty()) {
                    send(socket, server, "[" + username + "] : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur du client UDP : " + e.getMessage());
        }
    }

    private static void receiveMessages(DatagramSocket socket) {
        byte[] buffer = new byte[MAX_DATAGRAM_SIZE];
        while (!socket.isClosed()) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), packet.getOffset(),
                        packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("\n" + message);
                System.out.print("> ");
            } catch (SocketException e) {
                if (!socket.isClosed()) {
                    System.err.println("Réception interrompue : " + e.getMessage());
                }
                return;
            } catch (IOException e) {
                System.err.println("Impossible de recevoir un message : " + e.getMessage());
            }
        }
    }

    private static void send(DatagramSocket socket, InetSocketAddress server, String message)
            throws IOException {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length, server);
        socket.send(packet);
    }
}
