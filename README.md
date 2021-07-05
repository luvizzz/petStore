PetStore project

This project aims to test REST api hosted in https://petstore.swagger.io/

---
In order to execute the complete test suite, please run the following steps:

- Make sure to have Java 11 (or newer) installed
- Export JAVA_HOME variable pointing to your Java JDK
- Have a maven command available and runnable from terminal
- Download this repository content
- Open a new terminal session and change your directory to '<repository location>/petStore' (basically, the same directory as this readme file)
- Run 'mvn clean test'
- In order to generate a test report, run the command 'allure serve <repository location>/petStore/target/allure-results'

----
Technologies used:

- Java 11
- Maven
- Surefire plugin
- RestAssured
- Junit 5
- Allure report