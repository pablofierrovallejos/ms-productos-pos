CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarVentas`()
BEGIN
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
   from ventas
   where estadoreg = 'A';
END