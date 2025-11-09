package com.formacionbdi.springboot.app.productos.models.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.Auditoria;

public interface IAuditoriaService extends JpaRepository<Auditoria, Long> {

	@Modifying
	@Query(value = "{ call sp_insertarAuditoria(:hostorigen, :modulo, :accionrealizada, :usuario, :detalles, :latitud, :longitud, :ciudad, :region, :pais, :dataprocesada) }", nativeQuery = true)
	@Transactional
	public Object insertarAuditoria(
			String hostorigen,
			String modulo,
			String accionrealizada,
			String usuario,
			String detalles,
			Double latitud,
			Double longitud,
			String ciudad,
			String region,
			String pais,
			String dataprocesada
	);
}
