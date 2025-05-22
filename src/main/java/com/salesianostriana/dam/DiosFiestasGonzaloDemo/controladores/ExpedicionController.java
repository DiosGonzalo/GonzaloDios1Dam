package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ExpedicionController {

    @Autowired
    private ExpedicionServicio servicio;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

     
    @GetMapping("/expediciones")
    public String muestraExpediciones(Model model) {
        List<Expedicion> expediciones = servicio.aplicarDescuentos(servicio.findAll());
        model.addAttribute("expediciones", expediciones);
        return "expediciones";
    }
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("expedicion", new Expedicion());
        return "agregarExpedicion";
    }

    @PostMapping("/nueva/summit")
    public String procesarFormulario(@Validated @ModelAttribute("expedicion") Expedicion expedicion,BindingResult bindingResult,RedirectAttributes redirectAttributes) {

        if (expedicion.getFechaLimite() != null && expedicion.getFechaExpedicion() != null &&
            expedicion.getFechaLimite().isAfter(expedicion.getFechaExpedicion())) {
            bindingResult.rejectValue("fechaLimite", "error.fechaLimite", 
                                    "La fecha límite debe ser anterior a la fecha de expedición");
        }

        if (bindingResult.hasErrors()) {
            return "agregarExpedicion";
        }

        try {
            expedicion.setPrecioOriginal(expedicion.getPrecio());
            servicio.save(expedicion);
            redirectAttributes.addFlashAttribute("success", "Expedición creada correctamente");
            return "redirect:/expediciones";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la expedición: " + e.getMessage());
            return "redirect:/nueva";
        }
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
    public String verDetalleExpedicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Expedicion> expedicionOpt = servicio.findByIdOptional(id);
            
            if (expedicionOpt.isPresent()) {
                Expedicion expedicion = expedicionOpt.get();
                model.addAttribute("expedicion", expedicion);
                model.addAttribute("usuarios", expedicion.getUsuarios());
                
                long usuariosConDescuento = expedicion.getUsuarios().stream()
                    .filter(u -> servicio.usuarioTieneDescuento(u, expedicion))
                    .count();
                model.addAttribute("usuariosConDescuento", usuariosConDescuento);
                
                return "detalleExpedicion";
            } else {
                redirectAttributes.addFlashAttribute("error", "Expedición no encontrada");
                return "redirect:/expediciones";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar la expedición");
            return "redirect:/error";
        }
    }

    @GetMapping("/error")
    public String mostrarError(Model model, @RequestParam(required = false) String error,@RequestParam(required = false) String mensaje) {
        model.addAttribute("error", error != null ? error : "Error desconocido");
        model.addAttribute("mensaje", mensaje != null ? mensaje : "Ha ocurrido un error inesperado");
        return "error";
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