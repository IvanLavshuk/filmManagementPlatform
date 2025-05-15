# Базовый образ с Java 17
FROM eclipse-temurin:17-jdk-jammy

# Рабочая директория в контейнере
WORKDIR /app

# Копируем JAR-файл
#потом для папки libs  gradlew.bat build

COPY build/libs/shop-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, который слушает Spring Boot
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]