@echo off
echo Starting "zipkin-server.jar"...
start "" http://localhost:9411/zipkin/
java -jar D:\Tools\zipkin-server.jar
pause