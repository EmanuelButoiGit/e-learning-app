@echo off
echo Starting "kibana.bat"...
start "" http://localhost:9600/
cd /d D:\Tools\logstash-7.16.0\bin
.\logstash.bat -f D:\Tools\logstash-7.16.0\config\logstash.conf
pause