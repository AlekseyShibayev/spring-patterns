# spring-patterns

## 1. Паттерн {Названия ещё нет}, инкапсулирует Command в список в виде Callback и после терминальной операции делает for each по Command. Пример в HabrTest.
### Как это выглядит в клиентском коде:
* Habr on = testEntityFactory.habrBy(Status.ON)
* *   .addHabrUser(NAME)
* *   .addHabrUser(NAME2)
* *   .createOne();
### Как это работает:
1. habrBy - создает спринговый прототайп, который внутри себя содержит список EnrichCallback
2. addHabrUser - добавляет колбек в виде лямбды
3. терминальная операция createOne дергает TestEntityFactoryFinisher, он открывает транзакцию
4. создает минимально возможный объект Habr, прогоняет его по цепочке колбеков, закрывает транзакцию, возвращает настроенный объект
### Как это расширять:
1. HabrPrototype содержит метод with, который принимает функциональный интерфейс, т.е. мы может задать лямбду
2. Часто используемые действия можно выносить в отдельные методы спринговых бинов
