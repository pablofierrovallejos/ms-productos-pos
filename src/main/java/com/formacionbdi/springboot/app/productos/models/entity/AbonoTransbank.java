package com.formacionbdi.springboot.app.productos.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "abonostransbank")
public class AbonoTransbank implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_abono")
	@Temporal(TemporalType.DATE)
	private Date fechaAbono;
	
	@Column(name = "monto_venta", precision = 15, scale = 2)
	private BigDecimal montoVenta;
	
	@Column(name = "comisiones", precision = 15, scale = 2)
	private BigDecimal comisiones;
	
	@Column(name = "iva_comisiones", precision = 15, scale = 2)
	private BigDecimal ivaComisiones;
	
	@Column(name = "monto_anulaciones", precision = 15, scale = 2)
	private BigDecimal montoAnulaciones;
	
	@Column(name = "devolucion_comisiones", precision = 15, scale = 2)
	private BigDecimal devolucionComisiones;
	
	@Column(name = "iva_devolucion", precision = 15, scale = 2)
	private BigDecimal ivaDevolucion;
	
	@Column(name = "monto_cobros", precision = 15, scale = 2)
	private BigDecimal montoCobros;
	
	@Column(name = "iva_cobros", precision = 15, scale = 2)
	private BigDecimal ivaCobros;
	
	@Column(name = "total_abonos", precision = 15, scale = 2)
	private BigDecimal totalAbonos;
	
	@Column(name = "archivo_origen", length = 255)
	private String archivoOrigen;
	
	@Column(name = "fecha_procesamiento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaProcesamiento;
	
	public AbonoTransbank() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaAbono() {
		return fechaAbono;
	}

	public void setFechaAbono(Date fechaAbono) {
		this.fechaAbono = fechaAbono;
	}

	public BigDecimal getMontoVenta() {
		return montoVenta;
	}

	public void setMontoVenta(BigDecimal montoVenta) {
		this.montoVenta = montoVenta;
	}

	public BigDecimal getComisiones() {
		return comisiones;
	}

	public void setComisiones(BigDecimal comisiones) {
		this.comisiones = comisiones;
	}

	public BigDecimal getIvaComisiones() {
		return ivaComisiones;
	}

	public void setIvaComisiones(BigDecimal ivaComisiones) {
		this.ivaComisiones = ivaComisiones;
	}

	public BigDecimal getMontoAnulaciones() {
		return montoAnulaciones;
	}

	public void setMontoAnulaciones(BigDecimal montoAnulaciones) {
		this.montoAnulaciones = montoAnulaciones;
	}

	public BigDecimal getDevolucionComisiones() {
		return devolucionComisiones;
	}

	public void setDevolucionComisiones(BigDecimal devolucionComisiones) {
		this.devolucionComisiones = devolucionComisiones;
	}

	public BigDecimal getIvaDevolucion() {
		return ivaDevolucion;
	}

	public void setIvaDevolucion(BigDecimal ivaDevolucion) {
		this.ivaDevolucion = ivaDevolucion;
	}

	public BigDecimal getMontoCobros() {
		return montoCobros;
	}

	public void setMontoCobros(BigDecimal montoCobros) {
		this.montoCobros = montoCobros;
	}

	public BigDecimal getIvaCobros() {
		return ivaCobros;
	}

	public void setIvaCobros(BigDecimal ivaCobros) {
		this.ivaCobros = ivaCobros;
	}

	public BigDecimal getTotalAbonos() {
		return totalAbonos;
	}

	public void setTotalAbonos(BigDecimal totalAbonos) {
		this.totalAbonos = totalAbonos;
	}

	public String getArchivoOrigen() {
		return archivoOrigen;
	}

	public void setArchivoOrigen(String archivoOrigen) {
		this.archivoOrigen = archivoOrigen;
	}

	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}
}
