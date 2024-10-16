package com.formacionbdi.springboot.app.productos.models.service;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.EstadVentas;
import com.formacionbdi.springboot.app.productos.models.entity.VentaPos;

public interface IVentaServicePos extends JpaRepository<VentaPos, String>{

	
	
	@Query(value="{ call sp_listarVentas() }", nativeQuery = true)
	public List<VentaPos> listarVentasPos();
	

	@Query(value="{ call sp_consultarVentas(:fechaventa) }", nativeQuery = true)
	public List<VentaPos> consultarVenta(String fechaventa);
	

	@Query(value="{ call sp_estadisticaVentasMes(:fechaventa) }", nativeQuery = true)
	public List<EstadVentas> consultarEstadisticaMes(String fechaventa);
	
	
	
	
	@Modifying
	@Query(value="{ call sp_insertarVenta(:idventa, :fechaventa, :secuencia, :nroboleta, :totalarticulos, :subtotalventa, :iva, :totalimporte, :tipopago, :comisiontbk, :comunicacionpos, :estadotransbank, :trazastattransbk, :longmsgtransbank) }", nativeQuery = true)
	@Transactional
	public Object insertaVentaPos(
			  Long idventa,	
			  Date fechaventa,
			  String secuencia,
			  String nroboleta,
			  int totalarticulos,
			  int subtotalventa,
			  int iva,
			  int totalimporte,
			  String tipopago,
			  int comisiontbk,
			  String comunicacionpos,
			  String estadotransbank,
			  String trazastattransbk, 
			  String longmsgtransbank
			);

}
