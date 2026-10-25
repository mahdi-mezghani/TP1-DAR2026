
## Activité 2-2 — Échange d'objets (Serializable)

### Objectif
Envoyer un objet `Operation` (opérande1, opérateur, opérande2) du client
vers le serveur via des `ObjectOutputStream` / `ObjectInputStream`.

### Structure
- `Activite2-2/Operation.java` : classe `Serializable`
- `Activite2-2/Client.java`     : envoie l'objet et affiche le résultat
- `Activite2-2/Serveur.java`    : reçoit l'objet, calcule, renvoie le résultat

### Lancer
```bash
# Terminal 1
javac Activite2-2/*.java
java -cp Activite2-2 Serveur

# Terminal 2
java -cp Activite2-2 Client