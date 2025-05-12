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

   
   
   @PostConstruct
   public void agregarExpediciones() {
	   Expedicion a1 = new Expedicion(
               "Veleta", 
               99.99, 
               30, 
               0, 
               LocalDate.now().plusMonths(1).plusDays(25), 
               LocalDate.now().plusWeeks(3),
               "https://s2.abcstatics.com/abc/www/multimedia/viajar/2022/12/14/veleta-granada-portada2-U72274063406mlE-1200x630@abc.jpg"
			   );

           Expedicion a2 = new Expedicion(
               "Subida al Mulhacén", 
               299.99, 
               20, 
               1, 
               LocalDate.now().plusMonths(2).plusDays(20), 
               LocalDate.now().plusMonths(1),
               "https://upload.wikimedia.org/wikipedia/commons/e/e6/Mulhacen_north_face.JPG"
           );

           Expedicion a3 = new Expedicion(
               "Aneto", 
               799.99, 
               15, 
               2, 
               LocalDate.now().plusMonths(3).plusDays(10), 
               LocalDate.now().plusMonths(2),
               "https://imagenes.heraldo.es/files/image_1920_1080/uploads/imagenes/2018/01/22/_90043copia_009b536e.jpg");

           Expedicion a4 = new Expedicion(
               "MatterHorn", 
               1499.99, 
               10, 
               3, 
               LocalDate.now().plusMonths(4).plusDays(165), 
               LocalDate.now().plusMonths(3),
               "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlUfej-F4eYr-z7wUIZPfqm6vdL4s4zpeoag&s");
           save(a1);
           save(a2);
           save(a3);
           save(a4);
   }
    
   
   public List<Expedicion> buscarExpedicion(String nombre){
	   return repositorio.findAll().stream().filter(expedicion -> expedicion.getNombre().toLowerCase().contains(nombre)).toList();
   }
   
 
     
}