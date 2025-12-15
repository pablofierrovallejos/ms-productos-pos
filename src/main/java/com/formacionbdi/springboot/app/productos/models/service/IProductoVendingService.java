package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.productos.models.entity.ProductoVending;

public interface IProductoVendingService extends JpaRepository<ProductoVending, Long> {
    
    // Método para buscar por código
    Optional<ProductoVending> findByCodigo(String codigo);
    
    // Método para listar productos habilitados
    List<ProductoVending> findByHabilitadoTrue();
    
    // Método personalizado para actualizar solo la imagen
    @Modifying
    @Transactional
    @Query("UPDATE ProductoVending p SET p.imagen = :imagen WHERE p.id = :id")
    int updateImagenById(@Param("id") Long id, @Param("imagen") String imagen);
}
