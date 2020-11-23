FROM openjdk:8
EXPOSE 8080
ADD target/tutorial-0.0.1-SNAPSHOT.jar tutorial-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","tutorial-0.0.1-SNAPSHOT.jar"]
