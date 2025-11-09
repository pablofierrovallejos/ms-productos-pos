FROM eclipse-temurin:17-jre
VOLUME /tmp
EXPOSE 8001

# Configurar zona horaria America/Santiago
ENV TZ=America/Santiago
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./target/springboot-servicio-productos-0.0.1-SNAPSHOT.jar servicio-producto.jar
ENTRYPOINT ["java", "-Duser.timezone=America/Santiago", "-jar", "servicio-producto.jar"]
