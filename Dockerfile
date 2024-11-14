# Utiliser une image de base OpenJDK
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier l'artefact JAR généré par Maven dans le conteneur
ADD target/kaddem-0.0.1.jar app.jar

# Exposer le port utilisé par l'application Spring Boot
EXPOSE 8089

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
