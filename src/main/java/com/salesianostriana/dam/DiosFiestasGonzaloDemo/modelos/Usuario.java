package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
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
    private LocalDate fechaNacimiento;
    private String dni;
    private String nivel;
    
    @ManyToMany
    @JoinTable(
        name = "usuario_expedicion",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "expedicion_id")
    )
    private List<Expedicion> expediciones = new ArrayList<>();

    // Método para calcular la edad a partir de la fecha de nacimiento
    public int getEdad() {
        if (this.fechaNacimiento == null) {
            return 0;
        }
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }
}