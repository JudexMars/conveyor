# -----------------------------------------------------------------------------
# Этап 1: Сборка приложения (build stage)
# -----------------------------------------------------------------------------
# Используем JDK 21 для компиляции и сборки fat JAR
FROM eclipse-temurin:21-jdk AS builder

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы Gradle wrapper для кэширования зависимостей
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Даём права на выполнение Gradle wrapper
RUN chmod +x ./gradlew

# Копируем исходный код приложения
COPY src src

# Собираем fat JAR с помощью Shadow плагина
# --no-daemon отключает демон Gradle для экономии памяти в контейнере
RUN ./gradlew shadowJar --no-daemon

# -----------------------------------------------------------------------------
# Этап 2: Runtime (финальный образ)
# -----------------------------------------------------------------------------
# Используем легковесный JRE Alpine образ для запуска
FROM eclipse-temurin:21-jre-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR из builder этапа
COPY --from=builder /app/build/libs/conveyor-all.jar app.jar

# Указываем порт, на котором работает приложение
EXPOSE 8080

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]

