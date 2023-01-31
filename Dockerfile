FROM openjdk:11
MAINTAINER karod.com
#Можно прогнать maven instal
COPY target/tsm-0.0.1.jar tsm-0.0.1.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.config.location=./config/application.properties -jar tsm-0.0.1.jar"]
#ENTRYPOINT ["sh", "-c", "sleep 5000"]

