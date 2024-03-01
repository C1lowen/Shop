FROM openjdk:17

WORKDIR /app

COPY target/shop-0.0.1-SNAPSHOT.jar shop-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "shop-0.0.1-SNAPSHOT.jar"]