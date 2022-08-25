FROM openjdk:8
EXPOSE 8080
ADD target/email-notify-application.jar email-notify-application.jar
ENTRYPOINT ["java","-jar","email-notify-application.jar"]