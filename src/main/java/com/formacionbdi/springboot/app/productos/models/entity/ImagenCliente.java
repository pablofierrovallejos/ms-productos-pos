package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name = "imagenclientes")
public class ImagenCliente implements Serializable {
	
	private static final long serialVersionUID = -6312896027111373860L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idimagen;
	
	
	private Long idventa;

	@Lob
	String imagen;
	
	private String rutaimagen;

	public long getIdimagen() {
		return idimagen;
	}

	public void setIdimagen(long idimagen) {
		this.idimagen = idimagen;
	}

	public Long getIdventa() {
		return idventa;
	}

	public void setIdventa(Long idventa) {
		this.idventa = idventa;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getRutaimagen() {
		return rutaimagen;
	}

	public void setRutaimagen(String rutaimagen) {
		this.rutaimagen = rutaimagen;
	}

	
	
	
	
}
