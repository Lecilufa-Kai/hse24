Java version=openjdk version "11"

**how to build**<br>
At project root, <br>
npm install   (only run once) <br>
npm run build <br>
mvn clean package 

**how to run Unit Tests**<br>
At project root, mvn clean test, or directly run tests on your IDE.

**how to run Application**<br>
At project root, mvn spring-boot:run, or directly run it on your IDE.

**how to run with docker**<br>
1.At project root, proceed the steps of <b>how to build</b>   <br>
2.At project root, docker build -t kai/hse24-service . <br>
3.At project root, docker run -p 8123:8123 kai/hse24-service

**how to stop application in docker**<br>
1.At project root, docker ps , find the 'CONTAINER ID' <br>
2.At project root, docker stop 'CONTAINER ID' <br>

**Visit React UI**<br>
After application startup,<br> 
http://localhost:8123/index.html <br>
Usage of UI :  <br>
1.add Category via +, then add Product via + <br>
2.delete Category via -, delete Product via -


**API Documentation Swagger UI**<br>
After application startup,<br> 
http://localhost:8123/swagger-ui.html#/

**H2 DB web console**<br>
http://localhost:8123/h2-console <br>
JDBC URL:jdbc:h2:mem:test


