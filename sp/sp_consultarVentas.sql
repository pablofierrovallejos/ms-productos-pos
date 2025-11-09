CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_consultarVentas`(
IN fecventain varchar(10)
)
BEGIN
  DECLARE start_date datetime;
  DECLARE end_date datetime;
  
  SET start_date = (SELECT CAST( CONCAT(fecventain, ' 00:00:00') AS DATE));
  SET end_date = (SELECT STR_TO_DATE(  CONCAT(fecventain ,'23:59:59') ,'%Y-%m-%d %H:%i:%s')) ;
   
	select 
    idcorrelativo,
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
  from ventas WHERE fechaventa between start_date and end_date
  and estadoreg = 'A'; 
END