-- Script para modificar el campo imagen de la tabla producto_vending
-- Permite almacenar imágenes codificadas en base64 (aproximadamente 40 KB)
-- Base de datos: db_springboot_cloud

USE db_springboot_cloud;

-- Modificar el campo imagen para soportar imágenes en base64
-- Una imagen de 40 KB en base64 requiere aproximadamente 54 KB de almacenamiento
-- MEDIUMTEXT soporta hasta 16 MB, ideal para imágenes de productos
-- Equivalente a CLOB en otros SGBD como Oracle y SQL Server
ALTER TABLE producto_vending 
MODIFY COLUMN imagen MEDIUMTEXT COMMENT 'Imagen del producto codificada en base64 o emoji';

-- Verificar el cambio
DESCRIBE producto_vending;

-- Mostrar información del campo modificado
SELECT 
    COLUMN_NAME as 'Campo',
    COLUMN_TYPE as 'Tipo',
    IS_NULLABLE as 'Permite NULL',
    COLUMN_DEFAULT as 'Valor por defecto',
    CHARACTER_MAXIMUM_LENGTH as 'Longitud máxima',
    COLUMN_COMMENT as 'Comentario'
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'db_springboot_cloud' 
  AND TABLE_NAME = 'producto_vending' 
  AND COLUMN_NAME = 'imagen';

-- Ejemplo de actualización con imagen base64 (ejemplo reducido)
-- En producción, la cadena base64 será mucho más larga
/*
UPDATE producto_vending 
SET imagen = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=='
WHERE id = 1;
*/

SELECT 'Campo imagen modificado exitosamente para soportar imágenes en base64' as mensaje;
