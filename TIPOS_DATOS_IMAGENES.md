# Tipos de Datos para Imágenes en Bases de Datos

## 🔍 Comparativa de Tipos de Datos

### MySQL
| Tipo        | Capacidad Máxima    | Equivalente SQL Estándar | Uso Recomendado                |
|-------------|---------------------|--------------------------|--------------------------------|
| TINYTEXT    | 255 bytes           | -                        | Textos muy cortos              |
| TEXT        | 64 KB               | -                        | Imágenes muy pequeñas/iconos   |
| MEDIUMTEXT  | 16 MB               | **CLOB**                 | **Imágenes de productos** ✓    |
| LONGTEXT    | 4 GB                | **CLOB**                 | Imágenes de alta resolución    |

### Otros SGBD

**Oracle:**
```sql
ALTER TABLE producto_vending 
MODIFY imagen CLOB;
```

**SQL Server:**
```sql
ALTER TABLE producto_vending 
ALTER COLUMN imagen VARCHAR(MAX);
-- o
ALTER COLUMN imagen NVARCHAR(MAX);
```

**PostgreSQL:**
```sql
ALTER TABLE producto_vending 
ALTER COLUMN imagen TYPE TEXT;
```

## 📊 Decisión: ¿Por qué MEDIUMTEXT?

### Ventajas de MEDIUMTEXT sobre TEXT

| Aspecto              | TEXT (64 KB)    | MEDIUMTEXT (16 MB) |
|---------------------|-----------------|--------------------|
| Imagen pequeña (40KB) | ✓ Cabe          | ✓ Cabe             |
| Imagen media (200KB)  | ✗ No cabe       | ✓ Cabe             |
| Imagen HD (1MB)       | ✗ No cabe       | ✓ Cabe             |
| Flexibilidad          | Limitado        | ✓ Alta             |
| Rendimiento           | Más rápido      | Similar            |
| Espacio en disco      | Igual (dinámico)| Igual (dinámico)   |

### ⚠️ Nota Importante
En MySQL, tanto TEXT como MEDIUMTEXT **almacenan datos de forma dinámica**. No reservan el espacio máximo, solo usan lo que necesitan. Por lo tanto:

- Una imagen de 50 KB ocupará ~50 KB en disco tanto en TEXT como en MEDIUMTEXT
- La diferencia está en el **límite máximo** permitido
- **No hay penalización de rendimiento** por usar MEDIUMTEXT para imágenes pequeñas

## 💡 Recomendación Final

**Para el campo `imagen` en `producto_vending`:**

```sql
ALTER TABLE producto_vending 
MODIFY COLUMN imagen MEDIUMTEXT COMMENT 'Imagen del producto codificada en base64 o emoji';
```

### Razones:
1. ✓ **Flexibilidad:** Permite desde emojis hasta imágenes de alta calidad
2. ✓ **Sin desperdicio:** Solo usa el espacio necesario
3. ✓ **Estándar:** Equivalente a CLOB en otros SGBD
4. ✓ **Escalable:** Permite crecer sin migraciones futuras
5. ✓ **Rendimiento:** No hay diferencia práctica vs TEXT

## 🎯 Directrices de Tamaño de Imágenes

### Recomendaciones por Uso

| Caso de Uso           | Dimensiones  | Tamaño Original | Tamaño Base64 | Calidad JPEG |
|----------------------|-------------|-----------------|---------------|--------------|
| Thumbnail/Preview    | 100x100     | 5-10 KB         | 7-13 KB       | 70%          |
| Lista de productos   | 300x300     | 30-50 KB        | 40-67 KB      | 80%          |
| Detalle producto     | 800x800     | 150-300 KB      | 200-400 KB    | 85%          |
| Alta resolución      | 1920x1920   | 500 KB - 2 MB   | 667 KB - 2.7 MB| 90%          |

### Código Ejemplo: Redimensionar según Uso

```javascript
function obtenerCalidadPorUso(uso) {
  const configs = {
    'thumbnail': { maxWidth: 100, maxHeight: 100, quality: 0.7 },
    'lista': { maxWidth: 300, maxHeight: 300, quality: 0.8 },
    'detalle': { maxWidth: 800, maxHeight: 800, quality: 0.85 },
    'alta': { maxWidth: 1920, maxHeight: 1920, quality: 0.9 }
  };
  return configs[uso] || configs['lista'];
}

async function procesarImagen(file, uso = 'lista') {
  const config = obtenerCalidadPorUso(uso);
  return await redimensionarImagen(file, config.maxWidth, config.maxHeight, config.quality);
}
```

## 🔄 Migración desde Otros SGBD

### Desde Oracle
```sql
-- Oracle usa CLOB
-- Equivalente en MySQL: MEDIUMTEXT o LONGTEXT
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    DATA_LENGTH
FROM USER_TAB_COLUMNS
WHERE TABLE_NAME = 'PRODUCTO_VENDING'
  AND COLUMN_NAME = 'IMAGEN';

-- Exportar a MySQL: CLOB → MEDIUMTEXT
```

### Desde SQL Server
```sql
-- SQL Server usa VARCHAR(MAX) o TEXT (deprecated)
-- Equivalente en MySQL: MEDIUMTEXT
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'producto_vending'
  AND COLUMN_NAME = 'imagen';

-- Exportar a MySQL: VARCHAR(MAX) → MEDIUMTEXT
```

### Desde PostgreSQL
```sql
-- PostgreSQL usa TEXT (ilimitado en teoría)
-- Equivalente en MySQL: MEDIUMTEXT o LONGTEXT
\d+ producto_vending

-- Exportar a MySQL: TEXT → MEDIUMTEXT
```

## 📝 Consultas Útiles

### Verificar Tipo de Dato Actual
```sql
SHOW COLUMNS FROM producto_vending LIKE 'imagen';

-- o más detallado
SELECT 
    COLUMN_NAME,
    COLUMN_TYPE,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    COLUMN_COMMENT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'db_springboot_cloud'
  AND TABLE_NAME = 'producto_vending'
  AND COLUMN_NAME = 'imagen';
```

### Analizar Tamaños Actuales
```sql
SELECT 
    id,
    codigo,
    nombre,
    CASE 
        WHEN imagen LIKE 'data:image%' THEN 'Base64'
        WHEN LENGTH(imagen) <= 10 THEN 'Emoji'
        ELSE 'Otro'
    END as tipo_imagen,
    LENGTH(imagen) as bytes,
    ROUND(LENGTH(imagen) / 1024, 2) as kb,
    ROUND(LENGTH(imagen) / 1024 / 1024, 2) as mb,
    CASE 
        WHEN LENGTH(imagen) < 65535 THEN 'TEXT suficiente'
        WHEN LENGTH(imagen) < 16777215 THEN 'Requiere MEDIUMTEXT'
        ELSE 'Requiere LONGTEXT'
    END as tipo_requerido
FROM producto_vending
WHERE imagen IS NOT NULL
ORDER BY LENGTH(imagen) DESC;
```

### Estadísticas de Uso
```sql
SELECT 
    CASE 
        WHEN LENGTH(imagen) < 1024 THEN '<1 KB (emoji)'
        WHEN LENGTH(imagen) < 65535 THEN '1-64 KB'
        WHEN LENGTH(imagen) < 1048576 THEN '64 KB - 1 MB'
        WHEN LENGTH(imagen) < 16777215 THEN '1-16 MB'
        ELSE '>16 MB'
    END as rango_tamaño,
    COUNT(*) as cantidad,
    ROUND(AVG(LENGTH(imagen)) / 1024, 2) as promedio_kb,
    ROUND(MAX(LENGTH(imagen)) / 1024, 2) as maximo_kb,
    ROUND(SUM(LENGTH(imagen)) / 1024 / 1024, 2) as total_mb
FROM producto_vending
WHERE imagen IS NOT NULL
GROUP BY rango_tamaño
ORDER BY AVG(LENGTH(imagen));
```

## 🚀 Performance Tips

### 1. Indexación
```sql
-- NO indexar el campo imagen (inútil y costoso)
-- En su lugar, indexar campos relacionados
CREATE INDEX idx_tiene_imagen ON producto_vending(
    (CASE WHEN LENGTH(imagen) > 10 THEN 1 ELSE 0 END)
);
```

### 2. Consultas Eficientes
```sql
-- ✓ BUENO: Solo traer IDs para lista
SELECT id, codigo, nombre, precio, stock
FROM producto_vending;

-- ✗ MALO: Traer todas las imágenes base64
SELECT * FROM producto_vending;

-- ✓ BUENO: Traer imagen solo cuando se necesita
SELECT imagen 
FROM producto_vending 
WHERE id = 1;
```

### 3. Lazy Loading en la Aplicación
```java
// Usar proyección para evitar cargar imágenes innecesariamente
public interface ProductoVendingListDTO {
    Long getId();
    String getCodigo();
    String getNombre();
    Integer getPrecio();
    // NO incluir imagen en listas
}

// Cargar imagen solo en detalle
public ProductoVending findByIdWithImagen(Long id) {
    return repository.findById(id).orElse(null);
}
```

## 📚 Referencias

- [MySQL Text Types](https://dev.mysql.com/doc/refman/8.0/en/blob.html)
- [Oracle CLOB](https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/Data-Types.html#GUID-A3C0D836-BADB-44E5-A5D4-265BA5968483)
- [SQL Server Large Value Types](https://learn.microsoft.com/en-us/sql/t-sql/data-types/nchar-and-nvarchar-transact-sql)
- [PostgreSQL Text Types](https://www.postgresql.org/docs/current/datatype-character.html)
