package TP2.act2;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server objet démarré sur le port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connecté : " + socket.getInetAddress().getHostAddress());

                // OIS d'abord (le client crée OOS d'abord)
                try (ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                    Object obj = in.readObject();
                    if (!(obj instanceof Operation)) {
                        out.writeObject("Erreur : objet non reconnu");
                        out.flush();
                        continue;
                    }

                    Operation op = (Operation) obj;
                    System.out.println("Objet reçu : " + op);

                    Object resultat;
                    try {
                        resultat = calculer(op);
                    } catch (Exception e) {
                        resultat = "Erreur : " + e.getMessage();
                    }

                    out.writeObject(resultat);
                    out.flush();
                    System.out.println("Résultat envoyé : " + resultat);

                } catch (Exception e) {
                    System.err.println("Erreur client : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

    private static double calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        switch (op.getOperateur()) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("division par zéro");
                return a / b;
            default:  throw new IllegalArgumentException("opérateur inconnu");
        }
    }
}
