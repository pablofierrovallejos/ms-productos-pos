DELIMITER //

CREATE PROCEDURE `sp_insertarAuditoria`(
    IN p_hostorigen VARCHAR(100),
    IN p_modulo VARCHAR(100),
    IN p_accionrealizada VARCHAR(255),
    IN p_usuario VARCHAR(100),
    IN p_detalles TEXT,
    IN p_latitud DECIMAL(10, 8),
    IN p_longitud DECIMAL(11, 8),
    IN p_ciudad VARCHAR(100),
    IN p_region VARCHAR(100),
    IN p_pais VARCHAR(100),
    IN p_dataprocesada VARCHAR(5000)
)
BEGIN
    -- Insertar registro de auditoría con ubicación geográfica
    INSERT INTO auditoria (
        hostorigen,
        modulo,
        fechahora,
        accionrealizada,
        usuario,
        detalles,
        latitud,
        longitud,
        ciudad,
        region,
        pais,
        dataprocesada
    ) VALUES (
        p_hostorigen,
        p_modulo,
        NOW(),
        p_accionrealizada,
        p_usuario,
        p_detalles,
        p_latitud,
        p_longitud,
        p_ciudad,
        p_region,
        p_pais,
        p_dataprocesada
    );
    
    -- Retornar el ID del registro insertado
    SELECT LAST_INSERT_ID() as idregistro;
END //

DELIMITER ;
