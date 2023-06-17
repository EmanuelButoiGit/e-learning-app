@echo off
echo Starting "notification-service.jar"...
start "" http://localhost:8083/swagger-ui/index.html#/
java -jar D:\Tools\e-learning-jars\notification-service.jar
pause