CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarVenta`(
    IN id_correlativo BIGINT
)
BEGIN
    -- Actualizar el estado del registro a 'E' (Eliminado)
    UPDATE ventas 
    SET estadoreg = 'E'
    WHERE idcorrelativo = id_correlativo;
    
    -- Retornar el número de filas afectadas
    SELECT ROW_COUNT() as filasAfectadas;
END
