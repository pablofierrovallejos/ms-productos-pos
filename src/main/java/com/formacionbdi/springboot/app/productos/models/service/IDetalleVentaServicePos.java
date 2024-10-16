package com.formacionbdi.springboot.app.productos.models.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.DetalleVentaPos;

public interface IDetalleVentaServicePos extends JpaRepository<DetalleVentaPos, String>{


	
	@Modifying
	@Query(value="{ call sp_insertarDetalleVenta(:idventa, :nombreproducto, :idproducto, :cantidad, :preciosubtotal) }", nativeQuery = true)
	@Transactional
	public Object insertarDetalleVentaPos(
			  Long idventa,
			  String nombreproducto,
			  String idproducto,
			  int cantidad,
			  int preciosubtotal
			);

}
