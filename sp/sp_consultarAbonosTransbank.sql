DELIMITER //

CREATE PROCEDURE `sp_consultarAbonosTransbank`(
    IN p_mes VARCHAR(7)
)
BEGIN
    -- p_mes formato: 'YYYY-MM' ejemplo: '2025-12'
    
    SELECT 
        id,
        fecha_abono,
        monto_venta,
        comisiones,
        iva_comisiones,
        monto_anulaciones,
        devolucion_comisiones,
        iva_devolucion,
        monto_cobros,
        iva_cobros,
        total_abonos,
        archivo_origen,
        fecha_procesamiento
    FROM abonostransbank
    WHERE DATE_FORMAT(fecha_abono, '%Y-%m') = p_mes
    ORDER BY fecha_abono DESC;
    
END //

DELIMITER ;
