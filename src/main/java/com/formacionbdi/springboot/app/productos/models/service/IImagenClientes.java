package com.formacionbdi.springboot.app.productos.models.service;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.ImagenCliente;
import com.formacionbdi.springboot.app.productos.models.entity.ProductoPos;

public interface IImagenClientes extends JpaRepository<ImagenCliente, String>{

	
	
	@Modifying
	@Query(value="{ call sp_insertaImagenCliente(:idventa, :imagen, :rutaimagen) }", nativeQuery = true)
	@Transactional
	public Object insertaImagenCliente(long idventa, String imagen, String rutaimagen);
	
	
	@Query(value="{ call sp_consultaImagenCliente() }", nativeQuery = true)
	public  List<ImagenCliente>  consultarImagenCliente();
	
	
	@Query(value="{ call sp_consultaImagenCliente2(:sfecha) }", nativeQuery = true)
	public  List<ImagenCliente>  consultarImagenCliente2(String sfecha);
	
	
	
	
}
