-- Query para polling de nuevas transacciones (última hora menos 1 segundo)
SELECT * FROM db_springboot_cloud.ventas 
WHERE fechaventa >= DATE_SUB(NOW(), INTERVAL 1 SECOND)
ORDER BY fechaventa DESC;

-- CREAR ÍNDICE para optimizar consultas por fechaventa
CREATE INDEX idx_fechaventa ON db_springboot_cloud.ventas (fechaventa);

-- STORED PROCEDURE para polling de ventas con totales
DELIMITER //

CREATE PROCEDURE sp_pooling_vtas()
BEGIN
    DECLARE total_transacciones INT DEFAULT 0;
    DECLARE total_monto BIGINT DEFAULT 0;
    
    -- Obtener totales de las transacciones desde hace 1 segundo
    SELECT 
        COUNT(*) AS total_transacciones,
        COALESCE(SUM(totalimporte), 0) AS total_monto
    INTO total_transacciones, total_monto
    FROM db_springboot_cloud.ventas 
    WHERE fechaventa >= DATE_SUB(NOW(), INTERVAL 1 SECOND);
    
    -- Retornar las transacciones individuales
    --SELECT 
    --    *,
    --    total_transacciones AS total_transacciones_periodo,
    --    total_monto AS total_monto_periodo
    --FROM db_springboot_cloud.ventas 
    --WHERE fechaventa >= DATE_SUB(NOW(), INTERVAL 1 SECOND)
    --ORDER BY fechaventa DESC;
    
    -- Retornar resumen como segundo resultado set (opcional)
    --SELECT 
    --    total_transacciones AS total_transacciones,
    --    total_monto AS total_monto,
    --    DATE_SUB(NOW(), INTERVAL 1 SECOND) AS fecha_desde,
    --    NOW() AS fecha_hasta;
        
END //

DELIMITER ;

-- Para ejecutar el stored procedure:
-- CALL sp_pooling_vtas();

-- Para eliminar el SP si necesitas recrearlo:
-- DROP PROCEDURE IF EXISTS sp_pooling_vtas;

-- Alternativa: Índice compuesto si también consultas por otros campos frecuentemente
-- CREATE INDEX idx_fechaventa_estado ON db_springboot_cloud.ventas (fechaventa, estadotransbank);

-- Para verificar que el índice se creó correctamente
-- SHOW INDEX FROM db_springboot_cloud.ventas;

-- Para ver el plan de ejecución y confirmar que usa el índice
-- EXPLAIN SELECT * FROM db_springboot_cloud.ventas 
-- WHERE fechaventa >= DATE_SUB(NOW(), INTERVAL 1 SECOND)
-- ORDER BY fechaventa DESC;

-- Alternativa usando CURRENT_TIMESTAMP
-- SELECT * FROM db_springboot_cloud.ventas 
-- WHERE fechaventa >= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 SECOND)
-- ORDER BY fechaventa DESC;

-- Para debugging: ver la fecha/hora actual del servidor
-- SELECT NOW() as fecha_actual, DATE_SUB(NOW(), INTERVAL 1 SECOND) as fecha_limite;