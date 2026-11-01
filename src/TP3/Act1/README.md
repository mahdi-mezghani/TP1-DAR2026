
## TP3 – Activité 3-1 : Serveur multi-thread (acceptation de plusieurs clients)

### Objectif
Transformer le serveur de l'Activité 2-1 (communication par chaînes de caractères)
en un serveur multi-thread capable de gérer plusieurs clients simultanément,
chaque client étant traité dans un thread dédié (`ClientProcess`).

### Structure
- `Activite3-1/Serveur.java`       : `ServerSocket` + boucle `accept()` + spawn de threads
- `Activite3-1/ClientProcess.java` : `Runnable` gérant un client (affiche IP + n° d'ordre, calcule, répond)
- `Activite3-1/Client.java`        : client utilisant `InetAddress` / `InetSocketAddress`

### Points techniques
- Le serveur affiche pour chaque client son **adresse IP** (`getRemoteSocketAddress()`)
  et son **numéro d'ordre de connexion**.
- Le numéro d'ordre est renvoyé au client dès la connexion.
- Un thread est créé par client → plusieurs clients traités en parallèle.

### Lancer
```bash
javac Activite3-1/*.java
java -cp Activite3-1 Serveur
# puis, dans un autre terminal :
java -cp Activite3-1 Client