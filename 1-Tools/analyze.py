import os
import time

# initialize
root_path = "C:\Users\Emanuel\Documents\GitHub\e-learning-app"
global_token = "?"
services = [
    "media-service",
    "metrics-master",
    "notification-service",
    "rating-service",
    "recommendation-service",
    "starter-library"
]

# function declarations
def service_name(number):
    switcher = {
        0: "all",
        1: services[0],
        2: services[1],
        3: services[2],
        4: services[3],
        5: services[4],
        6: services[5]
    }
    return switcher.get(number, "Invalid input")

def print_dir():
  cwd = os.getcwd()
  print("The Current working directory is {0}".format(cwd))

def check_path(path, ifExist):
    if not ifExist:
        raise FileNotFoundError("The file or directory at {} does not exist".format(path))

start_time = time.time()
print("Script made by Butoi Emanuel-Sebastian\n")
print("Don't forget to run the .bat file as an Administrator\n")

count = 1
print("0: all projects/services")
for service in services:
    print(str(count) + ": " + service)
    count += 1
print("\n")

answer = input("Which project/service do you want to analyze? (0 - " + str(count - 1) + ")\n")
system = service_name(int(answer))
if system != "all":
    command = "gradlew.bat sonar -Dsonar.projectKey=" + system + " -Dsonar.projectName=" + system + " -Dsonar.host.url=http://localhost:9000 -Dsonar.token=" + global_token
    os.chdir(root_path)
    path = root_path + "\\" + system
    ifExist = os.path.exists(path)
    os.chdir(path)
    print_dir()
    os.system(command)
else: 
    for service in services:
        command = "gradlew.bat sonar -Dsonar.projectKey=" + service + " -Dsonar.projectName=" + service + " -Dsonar.host.url=http://localhost:9000 -Dsonar.token=" + global_token
        os.chdir(root_path)
        path = root_path + "\\" + service
        ifExist = os.path.exists(path)
        os.chdir(path)
        print_dir()
        os.system(command)
        path = root_path

print("DONE")
time_of_execution = (time.time() - start_time) / 60
print("--- time of execution: %s minutes ---" %str(time_of_execution))

if time_of_execution < 2:
    print("Most likely that you forgot to run the .bat file as an Administrator")
