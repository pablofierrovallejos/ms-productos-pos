package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;


import com.formacionbdi.springboot.app.productos.models.entity.CostosPos;

public interface ICostosServicePos extends JpaRepository<CostosPos, String>{

	
	@Query(value="{ call sp_consultarCostos() }", nativeQuery = true)
	public List<CostosPos> consultarCostos(String sfecha);
	
	
}
