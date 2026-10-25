# TP2 – Les sockets en mode connecté TCP (LSI3)

Nom & Prénom : Mezghani Mahdi
Groupe : LSI 3.2.1

## Activité 2-1 — Échange de chaînes de caractères (CC)

### Objectif
Permettre au client d'envoyer une opération complète (ex. `55 * 25`)
au serveur via des sockets TCP, le serveur effectue le calcul et
renvoie le résultat.

### Structure
- `Activite2-1/Client.java` : envoie l'opération sous forme de chaîne
- `Activite2-1/Serveur.java` : reçoit, valide, calcule et répond

### Lancer
```bash
# Terminal 1
javac Activite2-1/*.java
java -cp Activite2-1 Serveur

# Terminal 2
java -cp Activite2-1 Client