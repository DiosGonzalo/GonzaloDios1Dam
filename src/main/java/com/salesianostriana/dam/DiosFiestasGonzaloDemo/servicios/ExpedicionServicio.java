package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.ExpedicionRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;

import jakarta.annotation.PostConstruct;


@Service
public class ExpedicionServicio extends ServiciosBase<Expedicion,Long,ExpedicionRepositorio>{

   

   
   public List<Expedicion> buscarExpedicion(String nombre){
	   return repositorio.findAll().stream().filter(expedicion -> expedicion.getNombre().toLowerCase().contains(nombre)).toList();
   }
   
 public List<Expedicion> filtrarCategoria(int categoria){
    if(categoria == 4){
        return repositorio.findAll();
    }else{
	return  repositorio.findAll().stream().filter(expedicion -> expedicion.getCategoria() == categoria).toList(); 
        }
    }   
public List<Expedicion> ordenarPrecioMayor() {
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.sort((e1, e2) -> Double.compare(e2.getPrecio(), e1.getPrecio())); 
    return expediciones;
}

public List<Expedicion> ordenarPrecioMenor() {
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.sort((e1, e2) -> Double.compare(e1.getPrecio(), e2.getPrecio())); 
    return expediciones;
}

public List<Expedicion> ordenarFechaMayor() {
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.sort((e1, e2) -> e1.getFechaExpedicion().compareTo(e2.getFechaExpedicion()));
    return expediciones;
}

public List<Expedicion> ordenarFechaMenor() {
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.sort((e1, e2) -> -e1.getFechaExpedicion().compareTo(e2.getFechaExpedicion()));
    return expediciones;
}
//Logica de negocio

public List<Expedicion> descuentoFechaProxima(){
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.stream().filter(exp -> exp.getFechaExpedicion().isBefore(LocalDate.now().plusMonths(5))).toList();
    expediciones.forEach(exp -> exp.setPrecio(exp.getPrecio()-(exp.getPrecio()*10)/100));
    return expediciones;

}

public List<Expedicion> descuentoMuchoPrecio(){
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.stream().filter(exp -> exp.getPrecio() > 10000).toList();
    expediciones.forEach(exp -> exp.setPrecio(exp.getPrecio()-(exp.getPrecio()*15)/100));
    return expediciones;

}

public List<Expedicion> descuentoCantidadUsuarios(){
    List<Expedicion> expediciones = repositorio.findAll();
    expediciones.stream().filter(exp -> exp.getUsuarios().size()-exp.getCapacidad() < exp.getCapacidad()-(exp.getCapacidad()/2)).toList();
    expediciones.forEach(exp -> exp.setPrecio(exp.getPrecio()-(exp.getPrecio()*35)/100));
    return expediciones;
}

}