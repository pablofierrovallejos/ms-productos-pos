# Ejemplos de Prueba - API Producto Vending

## Configuración Inicial
- **Base URL:** `http://localhost:8080`
- **Content-Type:** `application/json`

---

## 1. Actualizar Imagen de Producto (Caso Principal)

### Ejemplo 1: Actualizar imagen del producto con ID 1
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥤"}'
```

### Ejemplo 2: Cambiar emoji de Coca Cola
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥫"}'
```

### Ejemplo 3: Actualizar imagen de varios productos
```bash
# Producto 7 - Papas
curl -X POST http://localhost:8080/api/productos/vending/7/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥔"}'

# Producto 13 - Snickers
curl -X POST http://localhost:8080/api/productos/vending/13/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🍫"}'

# Producto 19 - Café Latte
curl -X POST http://localhost:8080/api/productos/vending/19/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "☕"}'
```

### Ejemplo 4: Error - Producto no encontrado
```bash
curl -X POST http://localhost:8080/api/productos/vending/999/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥤"}'
```
**Respuesta esperada:**
```json
{
  "error": "Producto no encontrado",
  "id": 999
}
```

### Ejemplo 5: Error - Campo imagen vacío
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": ""}'
```
**Respuesta esperada:**
```json
{
  "error": "El campo 'imagen' es requerido"
}
```

---

## 2. Consultar Producto (Verificar actualización)

```bash
# Consultar producto ID 1 después de actualizar la imagen
curl http://localhost:8080/api/productos/vending/1
```

---

## 3. Listar Todos los Productos

```bash
curl http://localhost:8080/api/productos/vending/listar
```

---

## 4. Listar Solo Productos Habilitados

```bash
curl http://localhost:8080/api/productos/vending/habilitados
```

---

## 5. Crear Nuevo Producto

```bash
curl -X POST http://localhost:8080/api/productos/vending/crear \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "F1",
    "nombre": "Monster Energy",
    "precio": 3500,
    "stock": 12,
    "imagen": "⚡",
    "fila": 6,
    "posicion": 1,
    "relay": 25,
    "habilitado": true,
    "stockMinimo": 5,
    "categoria": "Energizantes",
    "descripcion": "Bebida energizante 500ml"
  }'
```

---

## 6. Actualizar Producto Completo

```bash
curl -X POST http://localhost:8080/api/productos/vending/1/actualizar \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

---

## Script PowerShell para Pruebas Completas

```powershell
# Script de prueba para API Producto Vending
$baseUrl = "http://localhost:8080/api/productos/vending"

# 1. Listar todos los productos
Write-Host "=== Listando todos los productos ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/listar" -Method Get | ConvertTo-Json -Depth 3

# 2. Consultar producto específico
Write-Host "`n=== Consultando producto ID 1 ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/1" -Method Get | ConvertTo-Json -Depth 2

# 3. Actualizar imagen
Write-Host "`n=== Actualizando imagen del producto ID 1 ===" -ForegroundColor Green
$body = @{
    imagen = "🥤"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/1/imagen" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body | ConvertTo-Json -Depth 2

# 4. Verificar actualización
Write-Host "`n=== Verificando actualización ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/1" -Method Get | ConvertTo-Json -Depth 2

Write-Host "`n=== Pruebas completadas ===" -ForegroundColor Cyan
```

---

## Colección Postman (JSON)

Guarda esto en un archivo `.json` e impórtalo en Postman:

```json
{
  "info": {
    "name": "Producto Vending API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Actualizar Imagen",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"imagen\": \"🥤\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/productos/vending/1/imagen",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "productos", "vending", "1", "imagen"]
        }
      }
    },
    {
      "name": "Listar Productos",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/productos/vending/listar",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "productos", "vending", "listar"]
        }
      }
    },
    {
      "name": "Consultar Producto por ID",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/productos/vending/1",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "productos", "vending", "1"]
        }
      }
    },
    {
      "name": "Crear Producto",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"codigo\": \"F1\",\n  \"nombre\": \"Monster Energy\",\n  \"precio\": 3500,\n  \"stock\": 12,\n  \"imagen\": \"⚡\",\n  \"fila\": 6,\n  \"posicion\": 1,\n  \"relay\": 25,\n  \"habilitado\": true,\n  \"stockMinimo\": 5,\n  \"categoria\": \"Energizantes\",\n  \"descripcion\": \"Bebida energizante 500ml\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/productos/vending/crear",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "productos", "vending", "crear"]
        }
      }
    }
  ]
}
```

---

## Emojis Recomendados por Categoría

### Bebidas
- 🥤 Gaseosas
- 🧃 Jugos
- 💧 Agua
- 🧊 Bebidas frías

### Cafés
- ☕ Café regular
- 🍵 Té
- 🥛 Café con leche

### Snacks
- 🥔 Papas fritas
- 🌮 Doritos/Nachos
- 🍿 Popcorn
- 🍪 Galletas
- 🥨 Pretzels

### Chocolates
- 🍫 Chocolate
- 🍬 Dulces

### Energizantes
- ⚡ Energy drinks
- 🔋 Bebidas energéticas

---

## Verificación de Datos

Después de ejecutar las pruebas, puedes verificar en la base de datos:

```sql
-- Ver todos los productos con sus imágenes
SELECT id, codigo, nombre, imagen, precio, stock 
FROM producto_vending 
ORDER BY fila, posicion;

-- Ver solo las actualizaciones recientes
SELECT id, codigo, nombre, imagen, updated_at 
FROM producto_vending 
WHERE updated_at > NOW() - INTERVAL 1 HOUR
ORDER BY updated_at DESC;
```
