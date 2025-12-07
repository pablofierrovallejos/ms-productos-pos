# ms-productos-pos



.\mvnw clean package -DskipTests

docker build -t servicio-productos:v13 .
docker tag servicio-productos:v13 96552333aa/servicio-productos:v13
docker push 96552333aa/servicio-productos:v13