FROM openjdk:8-jre-alpine
WORKDIR /spring-boot-app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/spring-boot-app/app.jar"]