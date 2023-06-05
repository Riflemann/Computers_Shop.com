# Computer Shop API
RESTful API для магизина по продаже компьютеров,

## Фунционал
Пользоватлеи могут:
* добавлять новые товары по типу
* редактировать товар по индефикатору
* получить список все товаров в наличии по типу
* получить список все товаров в наличии по индефикатору

### Технологий
* Java 17
* Spring Boot 3.0
* Hibernate JPA
* H2
* Maven
* UI API Swagger-ui

#### Подключенные библиотеки:
+ org.springframework.boot
+ org.projectlombok
+ com.h2database
+ org.springdoc
+ org.junit.jupiter

#### Для запуска:
Чтобы начать работу с проектом, на вашем локальном компьютере должно быть установлено следующее:
+ JDK 17+
+ Maven 3+

Чтобы собрать и запустить проект, выполните следующие действия:

+ Клонировать репозиторий: git clone https://github.com/Riflemann/Computers_Shop.com.git
+ Перейдите в каталог проекта: cd ComputerShop
+ Собрать проект: mvn clean install
+ Запустить проект: mvn spring-boot:run


-> Приложение будет доступно по ссылке http://localhost:8080.

