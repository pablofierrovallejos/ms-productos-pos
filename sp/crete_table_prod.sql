-- Script para crear tabla de productos para máquina vending
-- Base de datos: db_springboot_cloud

USE db_springboot_cloud;

-- Crear tabla producto_vending
CREATE TABLE IF NOT EXISTS producto_vending (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE COMMENT 'Código único del producto (A1, A2, B1, etc.)',
    nombre VARCHAR(100) NOT NULL COMMENT 'Nombre del producto',
    precio INT NOT NULL COMMENT 'Precio en pesos chilenos (sin decimales)',
    stock INT NOT NULL DEFAULT 0 COMMENT 'Cantidad disponible en stock',
    imagen VARCHAR(10) DEFAULT '??' COMMENT 'Emoji o ícono del producto',
    fila INT NOT NULL COMMENT 'Número de fila en la máquina (1-5)',
    posicion INT NOT NULL COMMENT 'Posición dentro de la fila (1-6)',
    relay INT NOT NULL UNIQUE COMMENT 'Número de relé del KC868-A16 (1-24)',
    habilitado BOOLEAN DEFAULT TRUE COMMENT 'Producto activo/visible en la máquina',
    stock_minimo INT DEFAULT 5 COMMENT 'Stock mínimo para alertas',
    categoria VARCHAR(50) DEFAULT 'General' COMMENT 'Categoría del producto',
    descripcion TEXT COMMENT 'Descripción detallada del producto',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_codigo (codigo),
    INDEX idx_fila (fila),
    INDEX idx_relay (relay),
    INDEX idx_habilitado (habilitado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar productos iniciales
INSERT INTO producto_vending (codigo, nombre, precio, stock, imagen, fila, posicion, relay, habilitado, categoria) VALUES
-- Fila 1 - Bebidas Gaseosas (Relés 1-3)
('A1', 'Coca Cola', 2500, 10, '??', 1, 1, 1, TRUE, 'Bebidas'),
('A2', 'Pepsi', 2500, 8, '??', 1, 2, 2, TRUE, 'Bebidas'),
('A3', 'Sprite', 2500, 12, '??', 1, 3, 3, TRUE, 'Bebidas'),

-- Fila 2 - Bebidas Saludables (Relés 4-6)
('B1', 'Agua Mineral', 1500, 15, '??', 2, 1, 4, TRUE, 'Bebidas'),
('B2', 'Jugo Naranja', 3000, 7, '??', 2, 2, 5, TRUE, 'Bebidas'),
('B3', 'Té Helado', 2800, 9, '??', 2, 3, 6, TRUE, 'Bebidas'),

-- Fila 3 - Snacks Salados (Relés 7-12)
('C1', 'Papas Lays', 1800, 20, '??', 3, 1, 7, TRUE, 'Snacks'),
('C2', 'Doritos', 2000, 18, '??', 3, 2, 8, TRUE, 'Snacks'),
('C3', 'Cheetos', 1900, 15, '??', 3, 3, 9, TRUE, 'Snacks'),
('C4', 'Pretzels', 1700, 12, '??', 3, 4, 10, TRUE, 'Snacks'),
('C5', 'Popcorn', 1600, 14, '??', 3, 5, 11, TRUE, 'Snacks'),
('C6', 'Galletas', 1500, 16, '??', 3, 6, 12, TRUE, 'Snacks'),

-- Fila 4 - Chocolates (Relés 13-18)
('D1', 'Snickers', 1200, 25, '??', 4, 1, 13, TRUE, 'Chocolates'),
('D2', 'Kit Kat', 1200, 22, '??', 4, 2, 14, TRUE, 'Chocolates'),
('D3', 'M&M''s', 1300, 20, '??', 4, 3, 15, TRUE, 'Chocolates'),
('D4', 'Twix', 1250, 18, '??', 4, 4, 16, TRUE, 'Chocolates'),
('D5', 'Milky Way', 1200, 19, '??', 4, 5, 17, TRUE, 'Chocolates'),
('D6', 'Reese''s', 1400, 17, '??', 4, 6, 18, TRUE, 'Chocolates'),

-- Fila 5 - Cafés y Energizantes (Relés 19-24)
('E1', 'Café Latte', 3500, 10, '?', 5, 1, 19, TRUE, 'Cafés'),
('E2', 'Cappuccino', 3500, 9, '?', 5, 2, 20, TRUE, 'Cafés'),
('E3', 'Espresso', 2500, 12, '?', 5, 3, 21, TRUE, 'Cafés'),
('E4', 'Chocolate', 3000, 8, '??', 5, 4, 22, TRUE, 'Cafés'),
('E5', 'Té Verde', 2800, 11, '??', 5, 5, 23, TRUE, 'Bebidas'),
('E6', 'Red Bull', 4000, 6, '??', 5, 6, 24, TRUE, 'Energizantes');

-- Vista para productos habilitados y con stock
CREATE OR REPLACE VIEW productos_disponibles AS
SELECT 
    id,
    codigo,
    nombre,
    precio,
    stock,
    imagen,
    fila,
    posicion,
    relay,
    categoria,
    CASE 
        WHEN stock <= stock_minimo THEN 'BAJO'
        WHEN stock = 0 THEN 'AGOTADO'
        ELSE 'NORMAL'
    END as estado_stock
FROM producto_vending
WHERE habilitado = TRUE
ORDER BY fila, posicion;

-- Vista para estadísticas de productos
CREATE OR REPLACE VIEW estadisticas_productos AS
SELECT 
    categoria,
    COUNT(*) as total_productos,
    SUM(stock) as stock_total,
    AVG(precio) as precio_promedio,
    SUM(CASE WHEN stock <= stock_minimo THEN 1 ELSE 0 END) as productos_stock_bajo,
    SUM(CASE WHEN stock = 0 THEN 1 ELSE 0 END) as productos_agotados
FROM producto_vending
WHERE habilitado = TRUE
GROUP BY categoria;

-- Mostrar datos insertados
SELECT 'Productos creados exitosamente:' as mensaje;
SELECT codigo, nombre, precio, stock, fila, posicion, relay FROM producto_vending ORDER BY fila, posicion;

-- Modificar el campo imagen para soportar imágenes en base64
-- Una imagen de 40 KB en base64 requiere aproximadamente 54 KB de almacenamiento
-- MEDIUMTEXT soporta hasta 16 MB, ideal para imágenes de productos
-- Equivalente a CLOB en otros SGBD (Oracle, SQL Server)
ALTER TABLE producto_vending 
MODIFY COLUMN imagen MEDIUMTEXT COMMENT 'Imagen del producto codificada en base64 o emoji';

-- 