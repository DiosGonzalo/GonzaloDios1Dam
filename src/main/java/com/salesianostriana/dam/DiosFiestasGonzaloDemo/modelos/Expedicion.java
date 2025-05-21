package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expedicion {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Double precio;
    
    @Column(nullable = false)
    private Double precioOriginal;
    
    @Column(nullable = false)
    private int capacidad;
    
    @Column(nullable = false)
    private int categoria;
    
    @Column(nullable = false)
    private String imagenUrl;

    @Transient
    private Integer plazasDisponibles;

    @ManyToMany(mappedBy = "expediciones")
    private List<Usuario> usuarios = new ArrayList<>();
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fechaExpedicion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fechaLimite;

    @Transient
    private String motivoDescuento;

    public Expedicion(String nombre, double precio, int capacidad, int categoria, 
                     LocalDate fechaExpedicion, LocalDate fechaLimite, String imagenUrl) {
        if (fechaLimite.isAfter(fechaExpedicion)) {
            throw new IllegalArgumentException("La fecha límite debe ser anterior a la fecha de expedición");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.precioOriginal = precio;
        this.capacidad = capacidad;
        this.categoria = categoria;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaLimite = fechaLimite;
        this.imagenUrl = imagenUrl;
    }

    public boolean tieneDescuento() {
        return precioOriginal != null && precio < precioOriginal;
    }

    public int getPorcentajeDescuento() {
        if (!tieneDescuento()) return 0;
        return (int) Math.round((1 - (precio / precioOriginal)) * 100);
    }

    public int getPlazasDisponibles() {
        if (plazasDisponibles == null) {
            plazasDisponibles = capacidad - usuarios.size();
        }
        return plazasDisponibles;
    }
}