package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idregistro;
	
	@Column(name = "hostorigen", length = 100)
	private String hostorigen;
	
	@Column(name = "modulo", length = 100)
	private String modulo;
	
	@Column(name = "fechahora")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechahora;
	
	@Column(name = "accionrealizada", length = 255)
	private String accionrealizada;
	
	@Column(name = "usuario", length = 100)
	private String usuario;
	
	@Column(name = "detalles", columnDefinition = "TEXT")
	private String detalles;
	
	@Column(name = "latitud", precision = 10, scale = 8)
	private Double latitud;
	
	@Column(name = "longitud", precision = 11, scale = 8)
	private Double longitud;
	
	@Column(name = "ciudad", length = 100)
	private String ciudad;
	
	@Column(name = "region", length = 100)
	private String region;
	
	@Column(name = "pais", length = 100)
	private String pais;
	
	@Column(name = "dataprocesada", length = 5000)
	private String dataprocesada;
	
	public Auditoria() {
	}

	public Long getIdregistro() {
		return idregistro;
	}

	public void setIdregistro(Long idregistro) {
		this.idregistro = idregistro;
	}

	public String getHostorigen() {
		return hostorigen;
	}

	public void setHostorigen(String hostorigen) {
		this.hostorigen = hostorigen;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Date getFechahora() {
		return fechahora;
	}

	public void setFechahora(Date fechahora) {
		this.fechahora = fechahora;
	}

	public String getAccionrealizada() {
		return accionrealizada;
	}

	public void setAccionrealizada(String accionrealizada) {
		this.accionrealizada = accionrealizada;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDataprocesada() {
		return dataprocesada;
	}

	public void setDataprocesada(String dataprocesada) {
		this.dataprocesada = dataprocesada;
	}
}
