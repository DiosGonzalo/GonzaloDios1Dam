package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
@Entity

public class Expedicion {

	
	@Id @GeneratedValue
    private Long id;
    
    private String nombre;
    private double precio;
    private int capacidad;
    private int categoria; 
    private String imagenUrl;
    @OneToMany  //Lo primero es para con lo que se relaciona, lo segundo es para como se trae las cosas    
    private List<Usuario> usuarios;
    
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

	public Expedicion(String string, double d, int i, String string2, LocalDate plusMonths, LocalDate plusMonths2,
			String string3, int j) {
	}

	


	
	
	
}
