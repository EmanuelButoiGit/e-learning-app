@echo off
echo Starting "prometheus.exe"...
start "" http://localhost:9090/
cd /d D:\Tools\prometheus-2.43.0
.\prometheus.exe
pause