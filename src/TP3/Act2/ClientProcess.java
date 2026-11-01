package TP3.Act2;

import java.io.*;
import java.net.*;

public class ClientProcess implements Runnable {
    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        // OOS créé en premier (côté qui écrit en premier) — évite le deadlock
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("Bienvenue, vous êtes le client n°" + numero);
            out.flush();

            while (true) {
                Object obj = in.readObject();          // EOFException = client déconnecté
                if (!(obj instanceof Operation)) {
                    out.writeObject(new Resultat(0, "objet non reconnu", false));
                    out.flush();
                    continue;
                }

                Operation op = (Operation) obj;
                System.out.println("[Client n°" + numero + "] opération reçue : " + op);

                Resultat resultat;
                try {
                    double valeur = calculer(op);
                    // (Étape 3) incrément + affichage synchronisé
                    int total = Server.incrementerCompteur();
                    System.out.println("Compteur global d'opérations traitées = " + total);
                    resultat = new Resultat(valeur, "OK", true);
                } catch (Exception e) {
                    resultat = new Resultat(0, e.getMessage(), false);
                }

                out.writeObject(resultat);
                out.flush();
            }
        } catch (EOFException eof) {
            // déconnexion normale du client
        } catch (Exception e) {
            System.err.println("[Client n°" + numero + "] erreur : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[Client n°" + numero + "] déconnecté");
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
            default: throw new IllegalArgumentException("opérateur inconnu");
        }
    }
}