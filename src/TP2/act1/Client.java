package TP2.act1;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connecté au serveur sur le port 5000");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entrez l'opération (ex: 55 * 25) : ");
            String saisie = userInput.readLine();

            // Validation syntaxique côté client
            String regex = "\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
            if (!saisie.matches(regex)) {
                System.out.println("Syntaxe invalide. Exemple attendu : 55 * 25");
                return;
            }

            // Envoi de l'opération sous forme de chaîne
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(saisie.trim());

            // Lecture du résultat
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String resultat = in.readLine();
            System.out.println("Résultat reçu du serveur : " + resultat);

        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}