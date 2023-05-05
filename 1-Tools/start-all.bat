@echo off
rem Start all servers:
start "" cmd /c "cd /d C:\Users\Emanuel\Documents\GitHub\e-learning-app\1-Tools && start start-discovery-server.bat"
start "" cmd /c "cd /d C:\Users\Emanuel\Documents\GitHub\e-learning-app\1-Tools && start start-zipkin-server.bat"
start "" cmd /c "cd /d C:\Users\Emanuel\Documents\GitHub\e-learning-app\1-Tools && start start-sonar-server.bat"
rem Start all the utility scripts:
start "" cmd /c "cd /d C:\Users\Emanuel\Documents\GitHub\e-learning-app\1-Tools && app-properties.bat"
start "" cmd /c "cd /d C:\Users\Emanuel\Documents\GitHub\e-learning-app\1-Tools && sonar-analyze.bat"