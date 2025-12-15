# Guía de Uso de Imágenes en Base64

## 📋 Descripción

El campo `imagen` de la tabla `producto_vending` ha sido modificado para soportar:
- **Emojis** (uso original): `🥤`, `🍫`, etc.
- **Imágenes codificadas en base64**: Imágenes reales del producto (hasta 16 MB)

**Tipo de dato:** MEDIUMTEXT (equivalente a CLOB en Oracle/SQL Server)

## 🔧 Modificación de la Base de Datos

### Script de Alteración
```sql
ALTER TABLE producto_vending 
MODIFY COLUMN imagen MEDIUMTEXT COMMENT 'Imagen del producto codificada en base64 o emoji';
```

### Especificaciones
- **Tipo anterior:** `VARCHAR(10)`
- **Tipo nuevo:** `MEDIUMTEXT` (equivalente a CLOB)
- **Capacidad:** Hasta 16,777,215 caracteres (16 MB)
- **Suficiente para:** Imágenes de alta calidad hasta varios MB

## 📸 ¿Qué es Base64?

Base64 es un método de codificación que convierte datos binarios (como imágenes) en texto ASCII, permitiendo almacenarlos en campos de texto en la base de datos.

### Ejemplo de Imagen en Base64
```
data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==
```

### Estructura
```
data:[MIME_TYPE];base64,[DATOS_CODIFICADOS]
```

Donde:
- `data:` - Prefijo del esquema de datos
- `image/png` - Tipo MIME (png, jpeg, gif, webp, etc.)
- `base64` - Método de codificación
- Datos codificados - La imagen convertida a texto

## 💻 Uso en el Endpoint

### Actualizar con Emoji (como antes)
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{"imagen": "🥤"}'
```

### Actualizar con Imagen Base64
```bash
curl -X POST http://localhost:8080/api/productos/vending/1/imagen \
  -H "Content-Type: application/json" \
  -d '{
    "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA..."
  }'
```

## 🔄 Conversión de Imagen a Base64

### JavaScript (Frontend)
```javascript
// Desde un input file
function convertirImagenABase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result);
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
}

// Uso
const input = document.getElementById('imagenInput');
input.addEventListener('change', async (e) => {
  const file = e.target.files[0];
  if (file) {
    const base64 = await convertirImagenABase64(file);
    console.log(base64); // data:image/png;base64,...
    
    // Enviar al servidor
    fetch('http://localhost:8080/api/productos/vending/1/imagen', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ imagen: base64 })
    });
  }
});
```

### JavaScript (Canvas)
```javascript
// Redimensionar y comprimir imagen antes de enviar
function redimensionarImagen(file, maxWidth = 300, maxHeight = 300) {
  return new Promise((resolve) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const img = new Image();
      img.onload = () => {
        const canvas = document.createElement('canvas');
        let width = img.width;
        let height = img.height;
        
        // Calcular nuevas dimensiones
        if (width > height) {
          if (width > maxWidth) {
            height *= maxWidth / width;
            width = maxWidth;
          }
        } else {
          if (height > maxHeight) {
            width *= maxHeight / height;
            height = maxHeight;
          }
        }
        
        canvas.width = width;
        canvas.height = height;
        const ctx = canvas.getContext('2d');
        ctx.drawImage(img, 0, 0, width, height);
        
        // Convertir a base64 con compresión
        resolve(canvas.toDataURL('image/jpeg', 0.8)); // 80% calidad
      };
      img.src = e.target.result;
    };
    reader.readAsDataURL(file);
  });
}
```

### Python
```python
import base64
from PIL import Image
import io

def imagen_a_base64(ruta_imagen, max_size=40000):
    """
    Convierte una imagen a base64, redimensionándola si es necesario
    para que no exceda max_size bytes
    """
    with Image.open(ruta_imagen) as img:
        # Redimensionar si es muy grande
        img.thumbnail((300, 300), Image.LANCZOS)
        
        # Guardar en buffer con compresión
        buffer = io.BytesIO()
        img.save(buffer, format='JPEG', quality=85, optimize=True)
        buffer.seek(0)
        
        # Codificar a base64
        img_base64 = base64.b64encode(buffer.read()).decode('utf-8')
        data_uri = f"data:image/jpeg;base64,{img_base64}"
        
        # Verificar tamaño
        size_kb = len(data_uri) / 1024
        print(f"Tamaño de la imagen: {size_kb:.2f} KB")
        
        return data_uri

# Uso
base64_string = imagen_a_base64('producto.jpg')

# Enviar al servidor
import requests
response = requests.post(
    'http://localhost:8080/api/productos/vending/1/imagen',
    json={'imagen': base64_string}
)
```

### Node.js
```javascript
const fs = require('fs');
const sharp = require('sharp'); // npm install sharp

async function imagenABase64(rutaImagen) {
  // Redimensionar y comprimir
  const buffer = await sharp(rutaImagen)
    .resize(300, 300, { fit: 'inside' })
    .jpeg({ quality: 80 })
    .toBuffer();
  
  // Convertir a base64
  const base64 = buffer.toString('base64');
  return `data:image/jpeg;base64,${base64}`;
}

// Uso
const base64String = await imagenABase64('producto.jpg');
console.log(`Tamaño: ${base64String.length / 1024} KB`);
```

## 📊 Tamaños y Recomendaciones

### Cálculo de Tamaño
| Tamaño Original | Tamaño Base64 | Tipo Campo MySQL     | Equivalente SQL    |
|----------------|---------------|----------------------|--------------------|
| 30 KB          | ~40 KB        | MEDIUMTEXT (16 MB)   | CLOB               |
| 100 KB         | ~133 KB       | MEDIUMTEXT (16 MB)   | CLOB               |
| 1 MB           | ~1.33 MB      | MEDIUMTEXT (16 MB)   | CLOB               |
| >10 MB         | ~13+ MB       | LONGTEXT (4 GB)      | CLOB               |

**Nota:** Base64 aumenta el tamaño en aproximadamente 33% (4/3 ratio)

### Recomendaciones
1. **Comprimir imágenes** antes de enviarlas
2. **Redimensionar** a máximo 300x300 píxeles para productos
3. **Usar JPEG** con calidad 80-85% para mejor compresión
4. **Limitar a 40 KB** para mantener rendimiento
5. **Considerar CDN** para aplicaciones de alta escala

### Buenas Prácticas
```javascript
// Validar tamaño antes de enviar
function validarTamanoImagen(base64String, maxKB = 50) {
  const sizeKB = (base64String.length * 0.75) / 1024; // Aproximado
  if (sizeKB > maxKB) {
    throw new Error(`Imagen muy grande: ${sizeKB.toFixed(2)} KB. Máximo: ${maxKB} KB`);
  }
  return true;
}
```

## 🎨 HTML Example - Formulario Completo

```html
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Actualizar Imagen Producto</title>
    <style>
        .container { max-width: 600px; margin: 50px auto; padding: 20px; }
        .preview { max-width: 300px; margin: 20px 0; }
        .preview img { max-width: 100%; border: 2px solid #ddd; }
        input, button { margin: 10px 0; padding: 10px; }
        button { background: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background: #45a049; }
        .info { color: #666; font-size: 12px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Actualizar Imagen del Producto</h2>
        
        <label>ID del Producto:</label>
        <input type="number" id="productoId" value="1" min="1">
        
        <label>Seleccionar Imagen:</label>
        <input type="file" id="imagenInput" accept="image/*">
        <div class="info">Máximo 40 KB - Se redimensionará automáticamente</div>
        
        <div class="preview" id="preview"></div>
        
        <button onclick="actualizarImagen()">Actualizar Imagen</button>
        
        <div id="resultado"></div>
    </div>

    <script>
        const imagenInput = document.getElementById('imagenInput');
        const preview = document.getElementById('preview');
        let imagenBase64 = null;

        imagenInput.addEventListener('change', async (e) => {
            const file = e.target.files[0];
            if (!file) return;

            // Mostrar vista previa
            const reader = new FileReader();
            reader.onload = (e) => {
                preview.innerHTML = `<img src="${e.target.result}" alt="Preview">`;
            };
            reader.readAsDataURL(file);

            // Redimensionar y comprimir
            imagenBase64 = await redimensionarImagen(file);
            const sizeKB = (imagenBase64.length / 1024).toFixed(2);
            preview.innerHTML += `<div class="info">Tamaño: ${sizeKB} KB</div>`;
        });

        async function redimensionarImagen(file) {
            return new Promise((resolve) => {
                const reader = new FileReader();
                reader.onload = (e) => {
                    const img = new Image();
                    img.onload = () => {
                        const canvas = document.createElement('canvas');
                        let width = img.width;
                        let height = img.height;
                        const maxDim = 300;

                        if (width > height) {
                            if (width > maxDim) {
                                height *= maxDim / width;
                                width = maxDim;
                            }
                        } else {
                            if (height > maxDim) {
                                width *= maxDim / height;
                                height = maxDim;
                            }
                        }

                        canvas.width = width;
                        canvas.height = height;
                        canvas.getContext('2d').drawImage(img, 0, 0, width, height);
                        resolve(canvas.toDataURL('image/jpeg', 0.8));
                    };
                    img.src = e.target.result;
                };
                reader.readAsDataURL(file);
            });
        }

        async function actualizarImagen() {
            const productoId = document.getElementById('productoId').value;
            const resultado = document.getElementById('resultado');

            if (!imagenBase64) {
                resultado.innerHTML = '<div style="color: red;">Por favor selecciona una imagen</div>';
                return;
            }

            try {
                resultado.innerHTML = '<div>Actualizando...</div>';
                
                const response = await fetch(`http://localhost:8080/api/productos/vending/${productoId}/imagen`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ imagen: imagenBase64 })
                });

                const data = await response.json();
                
                if (response.ok) {
                    resultado.innerHTML = `<div style="color: green;">✓ Imagen actualizada correctamente</div>`;
                    console.log('Producto actualizado:', data);
                } else {
                    resultado.innerHTML = `<div style="color: red;">✗ Error: ${data.error || 'Error desconocido'}</div>`;
                }
            } catch (error) {
                resultado.innerHTML = `<div style="color: red;">✗ Error de conexión: ${error.message}</div>`;
            }
        }
    </script>
</body>
</html>
```

## 🔍 Consultas SQL Útiles

```sql
-- Ver productos con imágenes base64
SELECT id, codigo, nombre, 
       CASE 
           WHEN imagen LIKE 'data:image%' THEN 'Base64'
           ELSE 'Emoji'
       END as tipo_imagen,
       LENGTH(imagen) as tamaño_bytes,
       ROUND(LENGTH(imagen)/1024, 2) as tamaño_kb
FROM producto_vending;

-- Productos con imágenes grandes
SELECT id, codigo, nombre, 
       ROUND(LENGTH(imagen)/1024, 2) as tamaño_kb
FROM producto_vending
WHERE LENGTH(imagen) > 1000
ORDER BY LENGTH(imagen) DESC;

-- Convertir imagen base64 de vuelta a emoji (si es necesario)
UPDATE producto_vending 
SET imagen = '🥤'
WHERE id = 1;
```

## ⚠️ Consideraciones de Rendimiento

### Ventajas de Base64 en BD
- ✓ No requiere servidor de archivos separado
- ✓ Datos atómicos (imagen con producto)
- ✓ Fácil sincronización y backup

### Desventajas
- ✗ Mayor tamaño de base de datos (33% más que binario)
- ✗ Consultas más lentas si hay muchas imágenes
- ✗ Mayor uso de memoria

### Alternativa Recomendada para Producción
Para aplicaciones de gran escala, considera:
1. Almacenar imágenes en un CDN (AWS S3, Cloudinary, etc.)
2. Guardar solo la URL en la base de datos
3. Usar base64 solo para previews pequeñas o thumbnails

```java
// Ejemplo de estructura alternativa
{
  "id": 1,
  "codigo": "A1",
  "nombre": "Coca Cola",
  "imagenUrl": "https://cdn.ejemplo.com/productos/coca-cola.jpg",
  "imagenThumbnail": "data:image/jpeg;base64,/9j/4AAQSkZJRg..." // Pequeña
}
```
