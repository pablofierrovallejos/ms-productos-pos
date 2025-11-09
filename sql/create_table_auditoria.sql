-- Tabla para auditoría de accesos y acciones del sistema
CREATE TABLE IF NOT EXISTS `auditoria` (
  `idregistro` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `hostorigen` VARCHAR(100) DEFAULT NULL COMMENT 'IP o host desde donde se realiza la acción',
  `modulo` VARCHAR(100) DEFAULT NULL COMMENT 'Módulo o endpoint accedido',
  `fechahora` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de la acción',
  `accionrealizada` VARCHAR(255) DEFAULT NULL COMMENT 'Descripción de la acción realizada',
  `usuario` VARCHAR(100) DEFAULT NULL COMMENT 'Usuario que realizó la acción',
  `detalles` TEXT DEFAULT NULL COMMENT 'Información adicional en formato JSON o texto',
  `latitud` DECIMAL(10, 8) DEFAULT NULL COMMENT 'Latitud de la ubicación geográfica',
  `longitud` DECIMAL(11, 8) DEFAULT NULL COMMENT 'Longitud de la ubicación geográfica',
  `ciudad` VARCHAR(100) DEFAULT NULL COMMENT 'Ciudad desde donde se realiza la acción',
  `region` VARCHAR(100) DEFAULT NULL COMMENT 'Región o estado',
  `pais` VARCHAR(100) DEFAULT NULL COMMENT 'País desde donde se realiza la acción',
  `dataprocesada` VARCHAR(5000) DEFAULT NULL COMMENT 'Data eliminada, insertada o modificada',
  PRIMARY KEY (`idregistro`),
  INDEX `idx_fechahora` (`fechahora`),
  INDEX `idx_modulo` (`modulo`),
  INDEX `idx_usuario` (`usuario`),
  INDEX `idx_pais` (`pais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de auditoría de accesos y acciones del sistema';

-- Script para agregar campos a tabla existente (si ya fue creada antes)
-- ALTER TABLE auditoria ADD COLUMN latitud DECIMAL(10, 8) DEFAULT NULL COMMENT 'Latitud de la ubicación geográfica';
-- ALTER TABLE auditoria ADD COLUMN longitud DECIMAL(11, 8) DEFAULT NULL COMMENT 'Longitud de la ubicación geográfica';
-- ALTER TABLE auditoria ADD COLUMN ciudad VARCHAR(100) DEFAULT NULL COMMENT 'Ciudad desde donde se realiza la acción';
-- ALTER TABLE auditoria ADD COLUMN region VARCHAR(100) DEFAULT NULL COMMENT 'Región o estado';
-- ALTER TABLE auditoria ADD COLUMN pais VARCHAR(100) DEFAULT NULL COMMENT 'País desde donde se realiza la acción';
-- ALTER TABLE auditoria ADD COLUMN dataprocesada VARCHAR(1000) DEFAULT NULL COMMENT 'Data eliminada, insertada o modificada';
-- ALTER TABLE auditoria ADD INDEX idx_pais (pais);
