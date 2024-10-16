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
@Table(name = "detalleventas")
public class DetalleVentaPos implements Serializable {

	private static final long serialVersionUID = 495358581041644038L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iddetalleventa;

	private Long idventa;
	private String nombreproducto;
	private String idproducto;
	

	private int cantidad;
	private int preciosubtotal;
	
	
	public DetalleVentaPos(){
	}


	public Long getIddetalleventa() {
		return iddetalleventa;
	}


	public void setIddetalleventa(Long iddetalleventa) {
		this.iddetalleventa = iddetalleventa;
	}


	public long getIdventa() {
		return idventa;
	}


	public void setIdventa(long idventa) {
		this.idventa = idventa;
	}


	public String getNombreproducto() {
		return nombreproducto;
	}


	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}


	public String getIdproducto() {
		return idproducto;
	}


	public void setIdproducto(String idproducto) {
		this.idproducto = idproducto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public int getPreciosubtotal() {
		return preciosubtotal;
	}


	public void setPreciosubtotal(int preciosubtotal) {
		this.preciosubtotal = preciosubtotal;
	}


}
