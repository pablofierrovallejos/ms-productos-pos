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
@Table(name = "ventas")
public class EstadVentas implements Serializable {

	private static final long serialVersionUID = 495358581041644038L;

		@Id
		@jakarta.persistence.Column(name = "namedia")
		private String namedia;

	
		private String value;


	   public String getNamedia() {
		   return namedia;
	   }

	   public void setNamedia(String namedia) {
		   this.namedia = namedia;
	   }


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	



}
