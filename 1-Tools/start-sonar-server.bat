@echo off
echo Starting "start-sonar-server.bat"...
start "" http://localhost:9000/
cd /d D:\Tools\sonarqube-10.0.0.68432\bin\windows-x86-64\
call StartSonar.bat
pause