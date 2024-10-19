package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
//import javax.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

import jakarta.persistence.TemporalType;

@Entity
@Table(name = "costos")
public class CostosPos implements Serializable {

	private static final long serialVersionUID = 495358581041644039L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idcostos;

	@Column(name = "fecha")
	private Date fecha;
	private String item;
	private int cantidad;
	private int montototal;
	
	private String descripcion;

	public long getIdcostos() {
		return idcostos;
	}

	public void setIdcostos(long idcostos) {
		this.idcostos = idcostos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getMontototal() {
		return montototal;
	}

	public void setMontototal(int montototal) {
		this.montototal = montototal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
