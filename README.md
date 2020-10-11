Java version=openjdk version "11"

**how to build**<br>
At project root, run mvn clean package

**how to run Unit Tests**<br>
At project root, run mvn clean test, or directly run tests on your IDE

**how to run Application**<br>
At project root, run mvn spring-boot:run, or directly run it on your IDE

**how to run with docker**<br>
1.At project root,run mvn clean package<br>
2.At project root,run docker build -t kai/currency-api . <br>
3.At project root,docker run -p 8123:8123 kai/currency-api