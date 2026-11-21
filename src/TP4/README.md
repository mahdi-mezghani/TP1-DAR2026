# TP4 - Chat client/serveur avec UDP

## Objectif

Cette activité met en place un chat en mode non connecté avec `DatagramSocket` et `DatagramPacket`. Le serveur écoute sur le port UDP `1234`, mémorise les clients après leur premier message et diffuse chaque message aux autres clients. Le client envoie et reçoit en parallèle grâce à un thread de réception.

## Fichiers

- `Server.java` : serveur UDP et diffusion aux clients enregistrés.
- `Client.java` : client interactif, préfixage des messages par le nom et réception asynchrone.

## Exécution dans IntelliJ IDEA

1. Ouvrir `Server.java` et lancer sa méthode `main`.
2. Lancer `Client.java` dans une nouvelle configuration d'exécution. Répéter cette étape pour ouvrir plusieurs clients.
3. Saisir un nom d'utilisateur dans chaque client, puis envoyer des messages. Chaque message apparaît chez les autres clients; l'expéditeur ne reçoit pas sa propre diffusion.
4. Saisir `/quit` pour fermer proprement un client et le retirer de la liste du serveur.

Par défaut, le client contacte `localhost`. Pour un serveur exécuté sur une autre machine, passer son adresse IP comme argument de programme du client, par exemple `192.168.1.20`. Le port UDP `1234` doit être autorisé par le pare-feu de la machine serveur.

## Synthèse

UDP ne crée pas de connexion persistante. Le serveur identifie donc chaque client par l'adresse et le port source du datagramme. Le premier message enregistre cette adresse; les messages suivants sont envoyés aux autres adresses mémorisées. Le client utilise un thread dédié à `receive()` pendant que le thread principal lit les saisies et envoie les datagrammes. Les textes sont encodés en UTF-8.

## Captures d'écran à ajouter au dépôt

Ajouter vos propres captures après exécution :

1. Le serveur démarré et deux clients enregistrés.
2. Un message saisi par un client et affiché chez l'autre.
3. La console serveur montrant l'expéditeur et le contenu reçu.

Les captures ne sont pas incluses ici, car elles doivent montrer une exécution réelle de votre environnement.
