-- Agregar columna estadoreg a la tabla ventas
-- Esta columna permitirá hacer eliminación lógica de registros
-- Valores: 'A' = Activo, 'E' = Eliminado

ALTER TABLE ventas 
ADD COLUMN estadoreg VARCHAR(1) DEFAULT 'A' COMMENT 'Estado del registro: A=Activo, E=Eliminado';

-- Actualizar registros existentes a estado 'A' (Activo)
UPDATE ventas 
SET estadoreg = 'A' 
WHERE estadoreg IS NULL;

-- Agregar índice para mejorar consultas filtradas por estado
CREATE INDEX idx_estadoreg ON ventas(estadoreg);

-- Verificar que la columna se agregó correctamente
-- SELECT * FROM ventas LIMIT 5;
