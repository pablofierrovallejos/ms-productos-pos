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
@Table(name = "productos")
public class ProductoPos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idproductos;
	
	private String nombreproducto;
	
	private int precio;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date fechacreacion;
	
	private String estado;
	
	private String nroposicion;
	
	private int cantidaddisponible;

	
	public ProductoPos(){
		
	}
	
	public Long getIdproductos() {
		return idproductos;
	}

	public void setIdproductos(Long idproductos) {
		this.idproductos = idproductos;
	}

	public String getNombreproducto() {
		return nombreproducto;
	}

	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNroposicion() {
		return nroposicion;
	}

	public void setNroposicion(String nroposicion) {
		this.nroposicion = nroposicion;
	}

	public int getCantidaddisponible() {
		return cantidaddisponible;
	}

	public void setCantidaddisponible(int cantidaddisponible) {
		this.cantidaddisponible = cantidaddisponible;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
