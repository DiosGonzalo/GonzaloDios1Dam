package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;
import org.springframework.web.bind.annotation.RequestBody;


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
	   model.addAttribute("expediciones",servicio.findAll());
	   return "expediciones";
   }
   
   @GetMapping("/expedicion/editar/{id}")
   @ResponseBody
   public Expedicion cargarExpedicionParaEditar(@PathVariable Long id) {
	   //Optional 
       return servicio.findById(id);
       //En el editar añadir un optional
	   //buscar el parametro error de thymeleaf para mas puntos agregar algun try catch
   }

   @PostMapping("/expedicion/editar")
   public String procesarEdicion(@ModelAttribute("expedicion") Expedicion expedicion) {
       servicio.edit(expedicion);
       return "redirect:/expediciones";
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
   
   @PostMapping("/expedicio/mayorPrecio")
   public String ordenarPrecioMayor(Model model) {
       model.addAttribute("expediciones",servicio.ordenarPrecioMayor());
       return "expediciones";
   }
	 
   @PostMapping("/expedicion/menorPrecio")
    public String ordenarPrecioMenor(Model model) {
        model.addAttribute("expediciones", servicio.ordenarPrecioMenor());
        return "expediciones";
    }

@PostMapping("/expedicion/fechaMayor")
public String ordenarFechasMayor(Model model){
    model.addAttribute("expediciones", servicio.ordenarFechaMayor());
    return "expediciones";
}
@PostMapping("/expedicion/fechaMenor")
public String ordenarFechaMenor(Model model){
    model.addAttribute("expediciones", servicio.ordenarFechaMenor());   
    return "expediciones";
}

   @GetMapping("/expediciones/filtrar")
public String muestraExpediciones(@RequestParam(value = "categoria") Integer categoria, Model model) {
    
    List<Expedicion> expediciones = categoria != null ? servicio.filtrarCategoria(categoria) : servicio.findAll();
    
    model.addAttribute("expediciones", servicio.filtrarCategoria(categoria));
    model.addAttribute("categoriaFiltro", categoria);
    return "expediciones";
}

@GetMapping("/expedicion/{id}")
public String verDetalleExpedicion(@PathVariable Long id, Model model) {
    Expedicion expedicion = servicio.findById(id);
    if (expedicion == null) {
        return "redirect:/expediciones";
    }
    model.addAttribute("expedicion", expedicion);
    model.addAttribute("usuarios", expedicion.getUsuarios());
    return "detalleExpedicion";
}

 
   
   

}