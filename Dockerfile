FROM openjdk:11

ADD target/transporter-app.jar transporter-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transporter-app.jar"]



