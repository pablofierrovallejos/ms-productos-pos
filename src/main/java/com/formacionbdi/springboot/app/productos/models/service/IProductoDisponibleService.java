package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formacionbdi.springboot.app.productos.models.entity.ProductoDisponible;

public interface IProductoDisponibleService extends JpaRepository<ProductoDisponible, Integer> {

    @Query(value = "SELECT * FROM productos_disponibles", nativeQuery = true)
    List<ProductoDisponible> findAllProductosDisponibles();
}
