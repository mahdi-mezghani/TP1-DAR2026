package TP2.act2;


import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5001)) {
            System.out.println("Connecté au serveur sur le port 5001");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entrez l'opération (ex: 55 * 25) : ");
            String saisie = userInput.readLine();

            String regex = "\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(saisie);
            if (!m.matches()) {
                System.out.println("Syntaxe invalide. Exemple attendu : 55 * 25");
                return;
            }

            Operation op = new Operation(
                    Double.parseDouble(m.group(1)),
                    m.group(2),
                    Double.parseDouble(m.group(3))
            );

            // 1) Envoi de l'objet (OOS d'abord pour éviter le deadlock)
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(op);
            out.flush();
            System.out.println("Objet envoyé : " + op);

            // 2) Réception du résultat
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object resultat = in.readObject();
            System.out.println("Résultat reçu du serveur : " + resultat);

        } catch (Exception e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}