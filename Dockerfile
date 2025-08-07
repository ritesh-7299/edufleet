 # import from official jdk image
 FROM openjdk:17-jdk-slim

 # set work direcotory inside container
 WORKDIR /app

 # copy jar file to container
 COPY target/edufleet-0.0.1-SNAPSHOT.jar app.jar

 # expose the port
 EXPOSE 7002

 # run command
 ENTRYPOINT ["java","-jar","app.jar"]