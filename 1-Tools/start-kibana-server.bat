@echo off
echo Starting "kibana.bat"...
start "" http://localhost:5601/app/discover
cd /d D:\Tools\kibana-7.9.2\bin
Call kibana.bat
pause