package TP3.Act1;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class ClientProcess implements Runnable {
    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // (c) envoi du numéro d'ordre au client
            out.println("Bienvenue, vous êtes le client n°" + numero);

            String ligne;
            while ((ligne = in.readLine()) != null) {
                System.out.println("[Client n°" + numero + "] opération reçue : " + ligne);
                if (ligne.equalsIgnoreCase("quit")) break;

                String resultat;
                try {
                    resultat = calculer(ligne);
                } catch (Exception e) {
                    resultat = "Erreur : " + e.getMessage();
                }
                out.println(resultat);
                System.out.println("[Client n°" + numero + "] résultat envoyé : " + resultat);
            }
        } catch (IOException e) {
            System.err.println("[Client n°" + numero + "] erreur : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[Client n°" + numero + "] déconnecté");
        }
    }

    private static String calculer(String operation) {
        String regex = "\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
        Matcher m = Pattern.compile(regex).matcher(operation);
        if (!m.matches()) throw new IllegalArgumentException("format invalide");

        double a = Double.parseDouble(m.group(1));
        String op = m.group(2);
        double b = Double.parseDouble(m.group(3));

        switch (op) {
            case "+": return String.valueOf(a + b);
            case "-": return String.valueOf(a - b);
            case "*": return String.valueOf(a * b);
            case "/":
                if (b == 0) throw new ArithmeticException("division par zéro");
                return String.valueOf(a / b);
            default: throw new IllegalArgumentException("opérateur inconnu");
        }
    }
}