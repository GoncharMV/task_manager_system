# Task Management System (TMS)

## Описание

Task Management System (TMS) — это система управления задачами, разработанная с использованием Spring Boot, PostgreSQL и Docker.
В проекте реализованы аутентификация, авторизация и ролевая модель доступа (ADMIN, USER).
Для документирования API используется Swagger.

## Технологии

Backend: Java 21, Spring Boot, Spring Security, JPA (Hibernate), PostgreSQL

API документация: OpenAPI (Swagger)
Контейнеризация: Docker, Docker Compose

Аутентификация: JWT

## Запуск проекта локально

1. Клонирование репозитория
   ```
    git clone https://github.com/GoncharMV/task-management-system.git
    cd task-management-system
   ```

2. Запуск с помощью Docker Compose
   ```
    docker-compose up --build
   ```
4. После успешного запуска API будет доступно по адресу: http://localhost:8060

## Документация API

Swagger UI доступен по адресу: http://localhost:8060/swagger-ui/index.html

## Работа с Postman

Файл TMS.postman_collection.json находится в корне проекта. Его можно импортировать в Postman для тестирования API.
      
