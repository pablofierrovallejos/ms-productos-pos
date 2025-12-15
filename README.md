# ms-productos-pos

Actualizar producto vending sin imagen

curl -X POST http://localhost:8080/api/productos/vending/1/actualizar \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "A1",
    "nombre": "Coca Cola 600ml",
    "precio": 2700,
    "stock": 20,
    "fila": 1,
    "posicion": 1,
    "relay": 1,
    "habilitado": true,
    "stockMinimo": 5,
    "categoria": "Bebidas"
  }'
  

.\mvnw clean package -DskipTests

docker build -t servicio-productos:v14 .
docker tag servicio-productos:v14 96552333aa/servicio-productos:v14
docker push 96552333aa/servicio-productos:v14

