# Render Farm Prototype (backend) Version 1.0

Проект представляет собой клиент-серверное приложение, реализующее прототип рендер-фермы.
Данное приложение позволяет отслеживать статус задач клиентов на рендер ферме.

1 Клиент - консольное приложение на Java со следующими возможностями:
- регистрация нового пользователя;
- создание новой задачи для пользователя;
- отображение списка созданных задач пользователя и статус задач.

2 Сервер - REST API на Java со Spring-boot-started-web, принимающее от клиента и обрабатывающее следующие запросы:
- регистрация пользователя с внесением его в базу данных (PostgreSQL);
- создание новой задачи для конкретного пользователя с занесением её в базу данных. После создания задачи ей присваивается статус RENDERING, по истечении случайного количества времени от 1 до 5 минут она считается выполненной и ей присваивается статус COMPLETE;
- обработка запроса списка текущих задач для конкретного пользователя.
