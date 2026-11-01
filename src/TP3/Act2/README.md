
## TP3 – Activité 3-2 : Calculatrice sur serveur multi-thread (objets) + compteur global

### Objectif
Reprendre le service de calculatrice de l'Activité 2-2 (communication par objets)
et l'intégrer dans le serveur multi-thread de l'Activité 3-1, avec un compteur
global partagé et synchronisé du nombre d'opérations traitées.

### Structure
- `Activite3-2/Operation.java`     : objet `Serializable` (opérandes + opérateur)
- `Activite3-2/Resultat.java`      : objet `Serializable` (valeur + message + succès)
- `Activite3-2/Serveur.java`       : `ServerSocket` + compteur global `synchronized`
- `Activite3-2/ClientProcess.java` : thread par client, E/R via `Object*Stream`
- `Activite3-2/Client.java`        : client objet, utilise `InetAddress` / `InetSocketAddress`

### Points techniques
- Communication **par objets** : `ObjectOutputStream` / `ObjectInputStream` des deux côtés
  (créés dans le même ordre pour éviter le deadlock).
- **Ressource critique** : `compteurOperations` est un champ `static int` accessible
  par tous les `ClientProcess`. Son incrémentation se fait dans une méthode
  `public static synchronized int incrementerCompteur()`, garantissant
  l'exclusion mutuelle entre threads.
- Chaque opération calculée : compteur incrémenté + valeur affichée dans la console serveur.

### Lancer
```bash
javac Activite3-2/*.java
java -cp Activite3-2 Serveur
# puis :
java -cp Activite3-2 Client