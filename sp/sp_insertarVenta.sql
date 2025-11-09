DELIMITER //

CREATE PROCEDURE `sp_insertarVenta`(
    IN p_idventa BIGINT,
    IN p_fechaventa DATETIME,
    IN p_secuencia VARCHAR(45),
    IN p_nroboleta VARCHAR(45),
    IN p_totalarticulos INT,
    IN p_subtotalventa INT,
    IN p_iva INT,
    IN p_totalimporte INT,
    IN p_tipopago VARCHAR(10),
    IN p_comisiontbk INT,
    IN p_comunicacionpos VARCHAR(45),
    IN p_estadotransbank VARCHAR(45),
    IN p_trazastattransbk VARCHAR(512),
    IN p_longmsgtransbank VARCHAR(1024)
)
BEGIN
    -- Insertar venta con estado 'A' (Activo) por defecto
    INSERT INTO ventas (
        idventa,
        fechaventa,
        secuencia,
        nroboleta,
        totalarticulos,
        subtotalventa,
        iva,
        totalimporte,
        tipopago,
        comisiontbk,
        comunicacionpos,
        estadotransbank,
        trazastattransbk,
        longmsgtransbank,
        estadoreg
    ) VALUES (
        p_idventa,
        p_fechaventa,
        p_secuencia,
        p_nroboleta,
        p_totalarticulos,
        p_subtotalventa,
        p_iva,
        p_totalimporte,
        p_tipopago,
        p_comisiontbk,
        p_comunicacionpos,
        p_estadotransbank,
        p_trazastattransbk,
        p_longmsgtransbank,
        'A'
    );
    
    -- Retornar el ID del registro insertado
    SELECT LAST_INSERT_ID() as idcorrelativo;
END //

DELIMITER ;
