package TP2.act1;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server démarré sur le port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connecté : " + socket.getInetAddress().getHostAddress());

                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String operation = in.readLine();
                    System.out.println("Opération reçue : " + operation);

                    String resultat;
                    try {
                        resultat = calculer(operation);
                    } catch (Exception e) {
                        resultat = "Erreur : " + e.getMessage();
                    }

                    out.println(resultat);
                    System.out.println("Résultat envoyé : " + resultat);

                } catch (IOException e) {
                    System.err.println("Erreur client : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

    private static String calculer(String operation) {
        String regex = "\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(operation);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("format invalide");
        }

        double a = Double.parseDouble(matcher.group(1));
        String operateur = matcher.group(2);
        double b = Double.parseDouble(matcher.group(3));

        switch (operateur) {
            case "+": return String.valueOf(a + b);
            case "-": return String.valueOf(a - b);
            case "*": return String.valueOf(a * b);
            case "/":
                if (b == 0) throw new ArithmeticException("division par zéro");
                return String.valueOf(a / b);
            default:  throw new IllegalArgumentException("opérateur inconnu");
        }
    }
}