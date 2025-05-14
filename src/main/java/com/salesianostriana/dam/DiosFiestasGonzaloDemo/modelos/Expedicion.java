package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expedicion {

    @Id 
    @GeneratedValue
    private Long id;
    
    private String nombre;
    private double precio;
    private int capacidad;
    private int categoria; 
    private String imagenUrl;
    
    @ManyToMany(mappedBy = "expediciones")
    private List<Usuario> usuarios = new ArrayList<>();
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaExpedicion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaLimite;

    public Expedicion(String nombre, double precio, int capacidad, int categoria, 
                     LocalDate fechaExpedicion, LocalDate fechaLimite, String imagenUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.capacidad = capacidad;
        this.categoria = categoria;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaLimite = fechaLimite;
        this.imagenUrl = imagenUrl;
    }
}