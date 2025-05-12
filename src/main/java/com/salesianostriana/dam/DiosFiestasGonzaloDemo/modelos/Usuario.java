package com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
	
	@Id	
	@GeneratedValue
	private long id;
	private String nombre;
	private String apellido;
	private int edad;
	private String dni;
	private String nivel;
	@ManyToOne
	private Expedicion e;
	
	
}
