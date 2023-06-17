@echo off
echo Starting "recommendation-service"...
start "" http://localhost:8082/swagger-ui/index.html#/
java -jar D:\Tools\e-learning-jars\recommendation-service.jar
pause