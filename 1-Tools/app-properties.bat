@echo off
echo Copying application.properties files...
copy D:\Tools\app.properties\media-service\application.properties C:\Users\Emanuel\Documents\GitHub\e-learning-app\media-service\src\main\resources\
copy D:\Tools\app.properties\media-service\test\application.properties C:\Users\Emanuel\Documents\GitHub\e-learning-app\media-service\src\test\resources\
copy D:\Tools\app.properties\notification-service\application.properties C:\Users\Emanuel\Documents\GitHub\e-learning-app\notification-service\src\main\resources\
copy D:\Tools\app.properties\rating-service\application.properties C:\Users\Emanuel\Documents\GitHub\e-learning-app\rating-service\src\main\resources\
echo Done.
pause