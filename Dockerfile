FROM openjdk:8
ADD target/Laundry-BE.jar Laundry-BE.jar
ENTRYPOINT ["java", "-jar","Laundry-BE.jar"]
EXPOSE 8081
