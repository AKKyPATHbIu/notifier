Зібрати додаток з запуском тестів
```
mvn clean install
```
Зібрати додаток без запуска тестів
```
mvn clean install -DskipTests
```
Запуск тестів
```
mvn test
```

Параметри конекта до бази даних зберігаються в файлі

```
/resources/connection.properties
```

Параметри відправників повідомлень зберігаються в файлі

```
/resources/notifier.properties
```

Кількість тасків для обробки  

```
recordCount=10
```

Період запуску обробки повідомлень (задається в мілісекундах)

```
fixedDelay.in.milliseconds=300000
```