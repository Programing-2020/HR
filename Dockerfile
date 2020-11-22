FROM openjdk:8
WORKDIR '/HR'
ADD target/hr-0.0.1.jar hr-0.0.1.jar
EXPOSE 8080
COPY . .
ENTRYPOINT ["java", "-jar", "hr-0.0.1.jar"]