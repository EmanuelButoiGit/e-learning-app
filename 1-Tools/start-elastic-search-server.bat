@echo off
echo Starting "elasticsearch.bat"...
start "" http://localhost:9200/
cd /d D:\Tools\elasticsearch-7.9.2\bin
Call elasticsearch.bat
pause