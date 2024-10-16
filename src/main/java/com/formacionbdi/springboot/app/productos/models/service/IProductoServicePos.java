package com.formacionbdi.springboot.app.productos.models.service;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.ProductoPos;
import com.formacionbdi.springboot.app.productos.models.entity.VentaPos;

public interface IProductoServicePos extends JpaRepository<ProductoPos, String>{

	@Query(value="{ call sp_listarProductos() }", nativeQuery = true)
	public List<ProductoPos> findAllProductsPos();
	
	@Query(value="{ call sp_consultarProducto(:idproductos) }", nativeQuery = true)
	public ProductoPos consultarId(int idproductos);
	
	@Modifying
	@Query(value="{ call sp_insertarProducto(:nombreproducto, :precio, :fechacreacion, :estado, :nroposicion, :cantidaddisponible, :imagen) }", nativeQuery = true)
	@Transactional
	public Object agregarProductoPos(String nombreproducto, int precio, Date fechacreacion, String estado,
			String nroposicion, int cantidaddisponible, Object imagen);
	
	
	@Modifying
	@Query(value="{ call sp_actualizarProducto(:idproductos, :nombreproducto, :precio, :fechacreacion, :estado, :nroposicion, :cantidaddisponible, :imagen) }", nativeQuery = true)
	@Transactional
	public Object actualizarProductoPos(Long idproductos, String nombreproducto, int precio, Date fechacreacion,
			String estado, String nroposicion, int cantidaddisponible, Object imagen);
	
	
	

	
	
}
