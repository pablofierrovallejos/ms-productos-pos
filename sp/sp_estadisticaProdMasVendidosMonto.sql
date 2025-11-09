CREATE DEFINER=`root`@`%` PROCEDURE `sp_estadisticaProdMasVendidosMonto`(
	IN fecventain varchar(10)
)
BEGIN

  DECLARE dfecventain date;
  SET dfecventain = (SELECT STR_TO_DATE(fecventain, '%Y-%m-%d'));
  
  CREATE TEMPORARY TABLE IF NOT EXISTS TablaTemporal ( 
		name varchar(50),
		value int(11)
  );    
    
INSERT INTO  TablaTemporal( 
SELECT   d.nombreproducto  as name,  sum(d.preciosubtotal) as value
FROM ventas as v,  detalleventas as d
where v.idventa = d.idventa
	and YEAR(v.fechaventa) = YEAR(dfecventain)
	and  MONTH(v.fechaventa) = MONTH(dfecventain)
    and v.estadoreg = 'A'
	GROUP BY   d.nombreproducto
);

SELECT name, value FROM TablaTemporal order by value desc;
drop table TablaTemporal;
    
    
END