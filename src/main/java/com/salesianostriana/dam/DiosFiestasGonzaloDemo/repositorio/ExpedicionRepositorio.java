package com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;

@Repository
public interface ExpedicionRepositorio extends JpaRepository<Expedicion, Long>{

    @Query("SELECT e FROM Expedicion e WHERE LOWER(e.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Expedicion> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT e FROM Expedicion e ORDER BY e.precio DESC")
    List<Expedicion> findAllOrderByPrecioDesc();

    @Query("SELECT e FROM Expedicion e ORDER BY e.precio ASC")
    List<Expedicion> findAllOrderByPrecioAsc();

    @Query("SELECT e FROM Expedicion e ORDER BY e.fechaExpedicion ASC")
    List<Expedicion> findAllOrderByFechaAsc();

    @Query("SELECT e FROM Expedicion e ORDER BY e.fechaExpedicion DESC")
    List<Expedicion> findAllOrderByFechaDesc();

    @Query("SELECT e FROM Expedicion e WHERE e.categoria = :categoria")
    List<Expedicion> findByCategoria(@Param("categoria") int categoria);
}



