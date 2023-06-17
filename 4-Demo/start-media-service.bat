@echo off
echo Starting "media-service.jar"...
start "" http://localhost:8080/swagger-ui/index.html#/
java -jar D:\Tools\e-learning-jars\media-service.jar
pause