@echo off
echo Starting "grafana-server.exe"...
start "" http://localhost:3000/d/X034JGT7Gz/springboot-apm-dashboard?orgId=1
cd /d D:\Tools\grafana-enterprise-9.4.7\bin
.\grafana-server.exe
pause