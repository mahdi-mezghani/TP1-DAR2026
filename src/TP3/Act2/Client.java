package TP3.Act2;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Client {
    public static void main(String[] args) {
        String hote = args.length > 0 ? args[0] : "localhost";
        int port    = args.length > 1 ? Integer.parseInt(args[1]) : 1235;

        try (Socket socket = new Socket()) {
            InetAddress adresse = InetAddress.getByName(hote);
            socket.connect(new InetSocketAddress(adresse, port), 5000);
            System.out.println("Connecté au serveur " + adresse.getHostAddress() + ":" + port);

            // OOS en premier, cohérent avec le serveur
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream());
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            // message de bienvenue (numéro d'ordre)
            System.out.println(in.readObject());

            while (true) {
                System.out.print("Opération (ex: 55 * 25) ou 'quit' : ");
                String saisie = clavier.readLine();
                if (saisie == null || saisie.equalsIgnoreCase("quit")) break;

                String regex = "\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*";
                Matcher m = Pattern.compile(regex).matcher(saisie);
                if (!m.matches()) {
                    System.out.println("Syntaxe invalide.");
                    continue;
                }

                Operation op = new Operation(
                        Double.parseDouble(m.group(1)),
                        m.group(2),
                        Double.parseDouble(m.group(3)));
                out.writeObject(op);
                out.flush();

                Object rep = in.readObject();
                if (rep instanceof Resultat) {
                    Resultat r = (Resultat) rep;
                    if (r.isSucces()) System.out.println("Résultat : " + r.getValeur());
                    else              System.out.println("Erreur : " + r.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}