package com.formacionbdi.springboot.app.productos.models.dto;

import java.io.Serializable;

public class EstadVentasDTO implements Serializable {
    private String namedia;
    private String value;

    public EstadVentasDTO(String namedia, String value) {
        this.namedia = namedia;
        this.value = value;
    }

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
