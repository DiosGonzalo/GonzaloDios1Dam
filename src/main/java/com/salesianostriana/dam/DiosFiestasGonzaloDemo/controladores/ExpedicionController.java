package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;

@Controller
public class ExpedicionController {


	@Autowired
    private  ExpedicionServicio servicio;


   

    

    @GetMapping("/")
    public String index(Model model) {
        
        return "Index"; 
    }
    
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("expedicion", new Expedicion());
        return "agregarExpedicion"; 
    }
    
    @PostMapping("/nueva/summit")
    public String procesarFormulario(@ModelAttribute("expedicion") Expedicion expedicion) {
        servicio.save(expedicion);
        return "redirect:/expediciones"; 
    }   
    
   @GetMapping("/expediciones")
   public String muestraExpediciones(Model model) {
	   System.out.println("asd");
	   model.addAttribute("expediciones",servicio.findAll());
	   System.out.println(servicio.findAll());
	   return "expediciones";
   }
   
   @GetMapping("/expedicion/editar/{id}")
   public String cargarExpedicionParaEditar(@PathVariable Long id, Model model) {
       Optional<Expedicion> expedicionOpt = Optional.ofNullable(servicio.findById(id));
       
       if (expedicionOpt.isPresent()) {
           model.addAttribute("expedicion", expedicionOpt.get());
           return "editarExpedicion"; // Nombre de tu vista de edición
       } else {
           // Expedición no encontrada
           model.addAttribute("error", "Expedición no encontrada con ID: " + id);
           return "redirect:/expediciones?error=expedicion_no_encontrada";
       }
   }

   @PostMapping("/expedicion/editar")
   public String procesarEdicion(@Validated @ModelAttribute("expedicion") Expedicion expedicion, BindingResult result, Model model) {
       
       // Validación de errores
       if (result.hasErrors()) {
           model.addAttribute("error", "Por favor, corrige los errores en el formulario");
           return "editarExpedicion"; // Volver a la vista de edición con errores
       }
       
       try {
           // Verificar si la expedición existe antes de editar
           Optional<Expedicion> existente = Optional.ofNullable(servicio.findById(expedicion.getId()));
           
           if (existente.isPresent()) {
               servicio.edit(expedicion);
               return "redirect:/expediciones?success=expedicion_actualizada";
           } else {
               model.addAttribute("error", "No se puede editar - Expedición no encontrada");
               return "redirect:/expediciones?error=expedicion_no_encontrada";
           }
       } catch (Exception e) {
           model.addAttribute("error", "Error al actualizar la expedición: " + e.getMessage());
           return "editarExpedicion"; // Volver a la vista de edición con error
       }
   }
   
   @PostMapping("/expedicion/buscar")
   public String buscarExpedicion(@RequestParam("nombre")String nombre, Model model ){
	  List<Expedicion> expediciones =nombre.isBlank() ? servicio.findAll() : servicio.buscarExpedicion(nombre);
          model.addAttribute("expediciones", servicio.buscarExpedicion(nombre));
           return "expediciones";
	   
    }
   @PostMapping("/expedicion/eliminar")
   public String eliminarExpedicion(@RequestParam("id") Long id) {
       servicio.deleteById(id);
       return "redirect:/expediciones";
   }


}