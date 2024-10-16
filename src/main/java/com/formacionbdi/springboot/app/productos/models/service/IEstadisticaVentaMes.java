package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.formacionbdi.springboot.app.productos.models.entity.EstadVentas;

public interface IEstadisticaVentaMes extends JpaRepository<EstadVentas, String>{

	
	@Query(value="{ call sp_estadisticaVentasMes(:fechaventa) }", nativeQuery = true)
	public List<EstadVentas> consultarEstadisticaMes(String fechaventa);
	
	
	@Query(value="{ call sp_estadisticaVentasMesProd(:fechaventa, :sproducto) }", nativeQuery = true)
	public List<EstadVentas> consultarEstadisticaMesProd(String fechaventa, String sproducto);
	

	@Query(value="{ call sp_estadisticaProdMasVendidosCantidad(:fechaventa) }", nativeQuery = true)
	public List<EstadVentas> consultarEstadisticaMesMasVendidoCant(String fechaventa);
	
	@Query(value="{ call sp_estadisticaProdMasVendidosMonto(:fechaventa) }", nativeQuery = true)
	public List<EstadVentas> consultarEstadisticaMesMasVendidoMonto(String fechaventa);
	

	
	
	
	
}