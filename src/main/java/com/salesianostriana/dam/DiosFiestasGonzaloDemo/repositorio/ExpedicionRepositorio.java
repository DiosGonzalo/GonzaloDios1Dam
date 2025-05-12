package com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;

@Repository
public interface ExpedicionRepositorio extends JpaRepository<Expedicion, Long>{

}
