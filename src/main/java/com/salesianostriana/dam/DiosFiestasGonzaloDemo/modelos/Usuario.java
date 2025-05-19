package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    
    @Id 
    @GeneratedValue
    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private String nivel;
    
    @ManyToMany
    @JoinTable(
        name = "usuario_expedicion",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "expedicion_id")
    )
    private List<Expedicion> expediciones = new ArrayList<>();
}