# Documentación API Producto Vending

## Endpoints para Productos Vending

### 1. Actualizar Imagen de Producto (Endpoint Principal Solicitado)

**Endpoint:** `POST /api/productos/vending/{id}/imagen`

**Descripción:** Permite agregar o reemplazar la imagen de un producto vending según su ID.

**Parámetros:**
- `id` (PathVariable): ID del producto a actualizar

**Body (JSON):**

Opción 1 - Emoji (uso simple):
```json
{
  "imagen": "🥤"
}
```

Opción 2 - Imagen Base64 (imagen real del producto):
```json
{
  "imagen": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBD..."
}
```

**Respuestas:**

✅ **200 OK** - Imagen actualizada correctamente
```json
{
  "id": 1,
  "codigo": "A1",
  "nombre": "Coca Cola",
  "precio": 2500,
  "stock": 10,
  "imagen": "🥤",
  "fila": 1,
  "posicion": 1,
  "relay": 1,
  "habilitado": true,
  "stockMinimo": 5,
  "categoria": "Bebidas",
  "descripcion": null,
  "createdAt": "2025-12-14T10:30:00",
  "updatedAt": "2025-12-14T15:45:00"
}
```

❌ **404 Not Found** - Producto no encontrado
```json
{
  "error": "Producto no encontrado",
  "id": 999
}
```

❌ **400 Bad Request** - Campo imagen vacío o nulo
```json
{
  "error": "El campo 'imagen' es requerido"
}
```

❌ **500 Internal Server Error** - Error del servidor
```json
{
  "error": "Error al actualizar la imagen",
  "mensaje": "Detalle del error..."
}
```

**Ejemplos de uso:**

**cURL:**
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥤"}'
```

**JavaScript (Fetch):**
```javascript
fetch('http://localhost:8080/api/productos/vending/1/imagen', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    imagen: '🥤'
  })
})
.then(response => response.json())
.then(data => console.log('Producto actualizado:', data))
.catch(error => console.error('Error:', error));
```

**Python (requests):**
```python
import requests

url = "http://localhost:8080/api/productos/vending/1/imagen"
payload = {"imagen": "🥤"}
response = requests.post(url, json=payload)

if response.status_code == 200:
    print("Imagen actualizada:", response.json())
else:
    print("Error:", response.json())
```

---

### 2. Listar Todos los Productos Vending

**Endpoint:** `GET /api/productos/vending/listar`

**Descripción:** Obtiene todos los productos vending (habilitados y deshabilitados)

**Respuesta:** Array de productos vending

---

### 3. Listar Productos Vending Habilitados

**Endpoint:** `GET /api/productos/vending/habilitados`

**Descripción:** Obtiene solo los productos vending habilitados

**Respuesta:** Array de productos vending habilitados

---

### 4. Consultar Producto por ID

**Endpoint:** `GET /api/productos/vending/{id}`

**Descripción:** Obtiene los detalles de un producto específico

**Parámetros:**
- `id` (PathVariable): ID del producto

**Ejemplo:**
```bash
curl http://localhost:8080/api/productos/vending/1
```

---

### 5. Crear Nuevo Producto

**Endpoint:** `POST /api/productos/vending/crear`

**Descripción:** Crea un nuevo producto vending

**Body (JSON):**
```json
{
  "codigo": "F1",
  "nombre": "Producto Nuevo",
  "precio": 1500,
  "stock": 20,
  "imagen": "🍫",
  "fila": 6,
  "posicion": 1,
  "relay": 25,
  "habilitado": true,
  "stockMinimo": 5,
  "categoria": "Snacks",
  "descripcion": "Descripción del producto"
}
```

---

### 6. Actualizar Producto Completo

**Endpoint:** `POST /api/productos/vending/{id}/actualizar`

**Descripción:** Actualiza todos los campos de un producto existente

**Parámetros:**
- `id` (PathVariable): ID del producto

**Body (JSON):**
```json
{
  "codigo": "A1",
  "nombre": "Coca Cola 500ml",
  "precio": 2600,
  "stock": 15,
  "imagen": "🥤",
  "fila": 1,
  "posicion": 1,
  "relay": 1,
  "habilitado": true,
  "stockMinimo": 3,
  "categoria": "Bebidas",
  "descripcion": "Bebida gaseosa 500ml"
}
```

---

## Estructura de la Entidad ProductoVending

```java
{
  "id": Long,                    // ID autogenerado
  "codigo": String,              // Código único (A1, B2, etc.)
  "nombre": String,              // Nombre del producto
  "precio": Integer,             // Precio en pesos chilenos
  "stock": Integer,              // Cantidad disponible
  "imagen": String,              // Emoji o imagen en base64 (MEDIUMTEXT/CLOB, hasta 16 MB)
  "fila": Integer,               // Número de fila (1-5)
  "posicion": Integer,           // Posición en la fila (1-6)
  "relay": Integer,              // Número de relé (1-24)
  "habilitado": Boolean,         // Si está activo
  "stockMinimo": Integer,        // Stock mínimo para alertas
  "categoria": String,           // Categoría del producto
  "descripcion": String,         // Descripción detallada
  "createdAt": Timestamp,        // Fecha de creación
  "updatedAt": Timestamp         // Última actualización
}
```

---

## Notas Importantes

1. **Validaciones:**
   - El campo `imagen` no puede estar vacío ni ser nulo
   - El producto debe existir antes de actualizar la imagen
   - El ID debe ser un número válido
   - Tamaño recomendado para imágenes base64: máximo 40-50 KB

2. **Formatos Soportados:**
   - **Emojis:** Cualquier emoji Unicode (🥤 🍫 🍪 ☕ 🧃 🥨 🍿 🍩)
   - **Imágenes Base64:** Imagen real del producto codificada en base64
   - Formatos de imagen: JPEG, PNG, GIF, WebP

3. **Imágenes Base64:**
   - Formato: `data:image/[tipo];base64,[datos_codificados]`
   - Tipo campo BD: `MEDIUMTEXT` (equivalente a CLOB, hasta 16 MB)
   - Recomendación: Comprimir y redimensionar antes de enviar (máx 800x800px para calidad)
   - Ver [IMAGEN_BASE64_GUIA.md](IMAGEN_BASE64_GUIA.md) para más detalles

4. **Base de datos:**
   - Tabla: `producto_vending`
   - Campo `imagen`: Tipo `MEDIUMTEXT` / CLOB (modificado desde VARCHAR(10))
   - El campo `updated_at` se actualiza automáticamente

5. **CORS:**
   - El controlador tiene habilitado `@CrossOrigin` para peticiones desde cualquier origen

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200    | Operación exitosa |
| 201    | Recurso creado |
| 400    | Petición incorrecta |
| 404    | Recurso no encontrado |
| 500    | Error del servidor |
