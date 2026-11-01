package TP3.Act2;

import java.io.Serializable;

public class Resultat implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double valeur;
    private final String message;
    private final boolean succes;

    public Resultat(double valeur, String message, boolean succes) {
        this.valeur  = valeur;
        this.message = message;
        this.succes  = succes;
    }

    public double getValeur()   { return valeur; }
    public String getMessage()  { return message; }
    public boolean isSucces()   { return succes; }

    @Override
    public String toString() {
        return succes ? String.valueOf(valeur) : "Erreur : " + message;
    }
}