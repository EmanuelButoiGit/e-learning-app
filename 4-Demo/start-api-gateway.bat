@echo off
echo Starting "api-gateway.jar"...
start "" http://localhost:8765/media-service/api/audio
java -jar D:\Tools\e-learning-jars\api-gateway.jar
pause