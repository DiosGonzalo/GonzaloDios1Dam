package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    
    @Id 
    @GeneratedValue
    private long id;
    private String nombre;
    private String apellido;
    private int edad; // Se mantiene como int
    private String dni;
    private String nivel;
    private String telefono;
    private String direccion;
    @Transient
    private double descuento;
    @Transient
    private String motivoDescuento;
    private String fechaRegistro; 
    
    @ManyToMany
    @JoinTable(
        name = "usuario_expedicion",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "expedicion_id")
    )
    private List<Expedicion> expediciones = new ArrayList<>();
    
    // Métodos de negocio
    public List<Expedicion> getExpedicionesActivas() {
        return expediciones.stream()
                .filter(e -> e.getFechaExpedicion().isAfter(LocalDate.now()))
                .toList();
    }
    
   
    
    public String getNivelCompleto() {
        return nivel + " (" + edad + " años)";
    }
}