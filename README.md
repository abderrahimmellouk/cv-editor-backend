---------CV Editor API - Backend (Spring Boot)
 Description
Cette application est le moteur (API REST) du projet CV Editor. Elle gère toute la logique métier, le stockage des données dans MySQL, la sécurité des utilisateurs et la gestion complexe des relations entre un CV et ses différentes sections (formations, expériences, etc.).
------ Architecture du Projet
Le projet suit une architecture logicielle en couches (Layered Architecture) pour garantir la maintenabilité et l'évolutivité :
1. Modèles & Entités (model/)
   User : Gère les comptes utilisateurs (Nom, Email, Password haché).
   CV : L'entité centrale liée à un utilisateur.
   Enfants (Formation, Experience, Stage, etc.) : Entités liées au CV par des relations One-to-Many.
   Gestion des relations : Utilisation de @JsonManagedReference et @JsonBackReference pour permettre l'envoi de données complètes sans créer de boucles infinies lors de la génération du JSON.
2. Couche Accès aux Données (repository/)
   Utilisation de Spring Data JPA.
   Interfaces étendant JpaRepository pour les opérations CRUD automatiques.
   Requêtes personnalisées pour filtrer les CV par utilisateur connecté.
3. Couche Métier (service/)
   CVServce : Centralise la logique d'enregistrement. Elle assure l'intégrité des données en liant manuellement les identifiants de CV aux formations/stages lors de la création.
   Sécurité : Implémentation du hachage de mot de passe en MD5 pour la protection des données sensibles.
4. Couche Contrôleur (controller/)
   AuthController : Gère le Register et le Login.
   CVController : Gère l'upload de fichiers (Photo de profil) via MultipartFile et la récupération des CV complets.
   Endpoints REST : Utilisation des verbes HTTP standards (GET, POST, PUT, DELETE).
   ⚙️ Choix Techniques
   Spring Boot 3 : Pour sa rapidité de configuration et sa robustesse.
   Hibernate : Pour la génération automatique des tables SQL (ddl-auto: update).
   MySQL (LONGBLOB) : Choisi pour stocker les photos de profil directement en base de données.
   CORS Configuration : Un filtre global (WebConfig) a été mis en place pour autoriser spécifiquement le frontend Angular (port 4200) à consommer les ressources de l'API.
   🛠️ Installation et Lancement
   Pré-requis
   Java JDK 17
   Maven 3.x
   XAMPP (ou MySQL Server)
   Étapes
   Base de données :
   Démarrer MySQL via XAMPP.
   Créer une base de données vide nommée cv_db.
   Configuration :
   Vérifier les accès dans src/main/resources/application.properties.
   Compilation :
  - Générer le fichier exécutable en ignorant les tests :
5. mvn clean install -DskipTests
6. mvn spring-boot:run