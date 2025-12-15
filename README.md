# ms-productos-pos



.\mvnw clean package -DskipTests

docker build -t servicio-productos:v14 .
docker tag servicio-productos:v14 96552333aa/servicio-productos:v14
docker push 96552333aa/servicio-productos:v14