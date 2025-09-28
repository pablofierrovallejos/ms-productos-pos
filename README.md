# ms-productos-pos



.\mvnw clean package -DskipTests

docker build -t servicio-productos:v12 .
docker tag servicio-productos:v12 96552333aa/servicio-productos:v12
docker push 96552333aa/servicio-productos:v12