package TP3.Act1;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        // adresse du serveur distant (test réseau local)
        String hote = args.length > 0 ? args[0] : "localhost";
        int port    = args.length > 1 ? Integer.parseInt(args[1]) : 1234;

        try (Socket socket = new Socket()) {
            InetAddress adresse = InetAddress.getByName(hote);
            socket.connect(new InetSocketAddress(adresse, port), 5000);
            System.out.println("Connecté au serveur " + adresse.getHostAddress() + ":" + port);

            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter   out  = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            // message de bienvenue contenant le numéro d'ordre
            System.out.println(in.readLine());

            while (true) {
                System.out.print("Opération (ex: 55 * 25) ou 'quit' : ");
                String saisie = clavier.readLine();
                if (saisie == null || saisie.equalsIgnoreCase("quit")) {
                    out.println("quit");
                    break;
                }
                out.println(saisie);
                System.out.println("Réponse serveur : " + in.readLine());
            }
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}