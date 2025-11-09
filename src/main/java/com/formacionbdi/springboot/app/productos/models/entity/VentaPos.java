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
public class VentaPos implements Serializable {

	private static final long serialVersionUID = 495358581041644038L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idcorrelativo;
	
	private long idventa;

	@Column(name = "fechaventa")
	//@Temporal(TemporalType.DATE)  esto hace que solo muestre Fecha sin hora
	
	@Temporal(TemporalType.TIME)
	private Date fechaventa;
	
	private String secuencia;
	private String nroboleta;
	private int totalarticulos;
	private int subtotalventa;
	private int iva;
	private int totalimporte;
	private String tipopago;
	private int comisiontbk;
	private String comunicacionpos;
	private String estadotransbank;
	private String trazastattransbk;
	private String longmsgtransbank;
	private String estadoreg;
	
	public VentaPos(){
	}

	public Long getIdcorrelativo() {
		return idcorrelativo;
	}

	public void setIdcorrelativo(Long idcorrelativo) {
		this.idcorrelativo = idcorrelativo;
	}

	public long getIdventa() {
		return idventa;
	}

	public void setIdventa(long idventa) {
		this.idventa = idventa;
	}

	public Date getFechaventa() {
		return fechaventa;
	}

	public void setFechaventa(Date fechaventa) {
		this.fechaventa = fechaventa;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getNroboleta() {
		return nroboleta;
	}

	public void setNroboleta(String nroboleta) {
		this.nroboleta = nroboleta;
	}

	public int getTotalarticulos() {
		return totalarticulos;
	}

	public void setTotalarticulos(int totalarticulos) {
		this.totalarticulos = totalarticulos;
	}

	public int getSubtotalventa() {
		return subtotalventa;
	}

	public void setSubtotalventa(int subtotalventa) {
		this.subtotalventa = subtotalventa;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public int getTotalimporte() {
		return totalimporte;
	}

	public void setTotalimporte(int totalimporte) {
		this.totalimporte = totalimporte;
	}

	public String getTipopago() {
		return tipopago;
	}

	public void setTipopago(String tipopago) {
		this.tipopago = tipopago;
	}

	public int getComisiontbk() {
		return comisiontbk;
	}

	public void setComisiontbk(int comisiontbk) {
		this.comisiontbk = comisiontbk;
	}

	public String getComunicacionpos() {
		return comunicacionpos;
	}

	public void setComunicacionpos(String comunicacionpos) {
		this.comunicacionpos = comunicacionpos;
	}

	public String getEstadotransbank() {
		return estadotransbank;
	}

	public void setEstadotransbank(String estadotransbank) {
		this.estadotransbank = estadotransbank;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTrazastattransbk() {
		return trazastattransbk;
	}

	public void setTrazastattransbk(String trazastattransbk) {
		this.trazastattransbk = trazastattransbk;
	}

	public String getLongmsgtransbank() {
		return longmsgtransbank;
	}

	public void setLongmsgtransbank(String longmsgtransbank) {
		this.longmsgtransbank = longmsgtransbank;
	}

	public String getEstadoreg() {
		return estadoreg;
	}

	public void setEstadoreg(String estadoreg) {
		this.estadoreg = estadoreg;
	}
	
	
	
}
