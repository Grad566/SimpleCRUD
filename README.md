## SimpleCRUD application

Приложение представляет собой простейший API сервис менеджера задач.

## Локальный запуск
Через docker-compose.

```
docker-compose up
```

После чего приложение будет доступно по http://localhost:8080/

### Документация
Документация swagger по url:
1) http://localhost:8080/swagger-ui/index.html
2) http://localhost:8080/v3/api-docs

### Запуск тестов
``` ./gradlew test```
Для некотрых тестов используется тест контейнер.
