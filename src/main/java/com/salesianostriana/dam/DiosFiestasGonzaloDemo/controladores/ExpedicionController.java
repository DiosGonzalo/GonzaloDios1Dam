package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpedicionController {

    @Autowired
    private ExpedicionServicio servicio;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
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
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.findAll());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }

    @GetMapping("/expedicion/editar/{id}")
    public String cargarExpedicionParaEditar(@PathVariable Long id, Model model) {
        Expedicion expedicion = servicio.findById(id);
        if (expedicion == null) {
            return "redirect:/expediciones";
        }
        model.addAttribute("expedicion", expedicion);
        return "editarExpedicion";
    }

    @PostMapping("/expedicion/editar")
    public String procesarEdicion(@ModelAttribute("expedicion") Expedicion expedicion) {
        servicio.save(expedicion);
        return "redirect:/expediciones";
    }

    @PostMapping("/expedicion/buscar")
    public String buscarExpedicion(@RequestParam("nombre") String nombre, Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.buscarExpedicion(nombre));
        model.addAttribute("expediciones", expediciones);
        model.addAttribute("nombreBusqueda", nombre);
        return "expediciones";
    }

     @PostMapping("/expedicion/eliminar")
    public String eliminarExpedicion(@RequestParam Long id) {
        servicio.deleteById(id);
        return "redirect:/expediciones";
    }

    @PostMapping("/expedicion/mayorPrecio")
    public String ordenarPrecioMayor(Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.ordenarPrecioMayor());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }

    @PostMapping("/expedicion/menorPrecio")
    public String ordenarPrecioMenor(Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.ordenarPrecioMenor());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }

    @PostMapping("/expedicion/fechaMayor")
    public String ordenarFechasMayor(Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.ordenarFechaMayor());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }

    @PostMapping("/expedicion/fechaMenor")
    public String ordenarFechaMenor(Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.ordenarFechaMenor());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }

    @GetMapping("/expediciones/filtrar")
    public String filtrarExpediciones(@RequestParam(value = "categoria") Integer categoria, Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(
            categoria != null ? servicio.filtrarCategoria(categoria) : servicio.findAll()
        );
        model.addAttribute("expediciones", expediciones);
        model.addAttribute("categoriaFiltro", categoria != null ? categoria : 4);
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

    @GetMapping("/sobreNosotros")
    public String sobreNosotros() {
        return "sobreNosotros";
    }

    @GetMapping("/carrusel")
    public String carrusel() {
        return "carrusel";
    }
}