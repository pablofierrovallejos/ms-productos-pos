package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formacionbdi.springboot.app.productos.models.entity.AbonoTransbank;

public interface IAbonoTransbankService extends JpaRepository<AbonoTransbank, Long> {

	@Query(value = "{ call sp_consultarAbonosTransbank(:mes) }", nativeQuery = true)
	public List<AbonoTransbank> consultarAbonosPorMes(String mes);
}
