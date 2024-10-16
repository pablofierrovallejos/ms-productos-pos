FROM openjdk:17
VOLUME /tmp
EXPOSE 8001
ADD ./target/springboot-servicio-productos-0.0.1-SNAPSHOT.jar servicio-producto.jar
ENTRYPOINT ["java", "-jar", "servicio-producto.jar"]
