@echo off
echo Starting "rating-service.jar"...
start "" http://localhost:8081/swagger-ui/index.html#/
java -jar D:\Tools\e-learning-jars\rating-service.jar
pause