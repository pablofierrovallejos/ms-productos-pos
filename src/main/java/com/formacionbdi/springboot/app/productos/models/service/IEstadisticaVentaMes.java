package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.formacionbdi.springboot.app.productos.models.entity.ProductoPos;

public interface IEstadisticaVentaMes extends JpaRepository<ProductoPos, Integer> {

	
		@Query(value = "{ call sp_estadisticaVentasMes(:fechaventa) }", nativeQuery = true)
		List<Object[]> consultarEstadisticaMesRaw(String fechaventa);
	
	
		@Query(value = "{ call sp_estadisticaVentasMesProd(:fechaventa, :sproducto) }", nativeQuery = true)
		List<Object[]> consultarEstadisticaMesProdRaw(String fechaventa, String sproducto);
	

		@Query(value = "{ call sp_estadisticaProdMasVendidosCantidad(:fechaventa) }", nativeQuery = true)
		List<Object[]> consultarEstadisticaMesMasVendidosCantidadRaw(String fechaventa);
	
		@Query(value = "{ call sp_estadisticaProdMasVendidosMonto(:fechaventa) }", nativeQuery = true)
		List<Object[]> consultarEstadisticaMesMasVendidosMontoRaw(String fechaventa);
	

	
	
	
	
}