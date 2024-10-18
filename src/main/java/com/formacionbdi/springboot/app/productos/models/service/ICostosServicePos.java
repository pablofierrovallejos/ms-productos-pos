package com.formacionbdi.springboot.app.productos.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.CostosPos;

public interface ICostosServicePos extends JpaRepository<CostosPos, String>{

	
	@Query(value="{ call sp_consultarCostos(:sfecha) }", nativeQuery = true)
	public List<CostosPos> consultarCostos(String sfecha);
	
	
	@Modifying
	@Query(value="{ call sp_insertarCostos(:idcostos, :fecha, :item, :cantidad, :montototal, :descripcion) }", nativeQuery = true)
	@Transactional
	public Object agregarCostosPos(
			  Date fecha,
			  String item,
			  int cantidad,
			  int montototal,
			  String descripcion
			);
	
	
}
