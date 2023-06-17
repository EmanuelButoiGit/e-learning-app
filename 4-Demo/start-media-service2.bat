@echo off
echo Starting "media-service.jar"...
start "" http://localhost:8880/api/audio
java -jar -Dserver.port=8880 D:\Tools\e-learning-jars\media-service.jar
pause