# Notes API

REST API для управления заметками. Учебный проект на Spring Boot.

## О проекте

REST API для создания, чтения, обновления и удаления заметок.
Поддерживает поиск по тексту, пагинацию, сортировку и валидацию данных.

## Технологии

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- JUnit 5
- Mockito
## Возможности

- CRUD операции с заметками
- Поиск по заголовку и содержанию (без учёта регистра)
- Пагинация и сортировка
- Валидация входных данных
- Обработка ошибок (404, 400)
- Логирование

## Быстрый старт

### Требования

- Java 17 или выше
- Maven (или Maven Wrapper)

### Запуск

Клонировать репозиторий:

git clone https://github.com/Lowdude1/notes-api.git

Перейти в папку проекта:

cd notes-api

Запустить приложение:

./mvnw spring-boot:run

Для Windows:

mvnw.cmd spring-boot:run

Приложение запустится на http://localhost:8080

## API Endpoints

| Метод | URL | Описание |
|-------|-----|----------|
| POST | /api/notes | Создать заметку |
| GET | /api/notes | Получить все заметки |
| GET | /api/notes/{id} | Получить заметку по ID |
| PUT | /api/notes/{id} | Обновить заметку |
| DELETE | /api/notes/{id} | Удалить заметку |
| GET | /api/notes/search?q= | Поиск по тексту |
| GET | /api/notes/page?page=0&size=10&sort=id,desc | Пагинация и сортировка |

## Примеры запросов

Создать заметку:

curl -X POST http://localhost:8080/api/notes \
-H "Content-Type: application/json" \
-d '{"title":"Моя заметка","content":"Текст заметки"}'

Ответ:

{
"id": 1,
"title": "Моя заметка",
"content": "Текст заметки",
"createdAt": "2025-04-04T15:30:00",
"updatedAt": "2025-04-04T15:30:00"
}

Получить все заметки:

curl http://localhost:8080/api/notes

Поиск:

curl "http://localhost:8080/api/notes/search?q=заметка"

Пагинация:

curl "http://localhost:8080/api/notes/page?page=0&size=5&sort=createdAt,desc"

## Структура проекта

src/
    main/java/com/example/notesapp/
        controller/          - REST контроллеры
        service/             - Бизнес-логика
        repository/          - Работа с БД
        entity/              - Сущности
        exception/           - Обработка ошибок
    resources/
        application.yml      - Конфигурация

## Автор

Козлов Егор
GitHub: Lowdude1