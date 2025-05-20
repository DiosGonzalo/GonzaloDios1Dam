package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioServicio usuarioServicio;
    private final ExpedicionServicio expedicionServicio;

    @GetMapping("/usuarios")
    public String listaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.findAll());
        model.addAttribute("todasExpediciones", expedicionServicio.findAll());
        return "usuarios";
    }

    @GetMapping("/usuario/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("expediciones", expedicionServicio.findAll());
        return "agregarUsuario";
    }

    @PostMapping("/usuario/nuevo/submit")
    public String guardarUsuario(@ModelAttribute Usuario usuario, 
                               @RequestParam(value = "expedicionesSeleccionadas", required = false) List<Long> expedicionesIds) {
        if(expedicionesIds != null && !expedicionesIds.isEmpty()) {
            usuario.setExpediciones(expedicionServicio.findAllById(expedicionesIds));
        }
        usuarioServicio.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuario/editar/{id}")
    public String verEdicionUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("todasExpediciones", expedicionServicio.findAll());
        return "editarUsuario";
    }

    @PostMapping("/usuario/editar/submit")
    public String editarUsuario(@ModelAttribute Usuario usuario, 
                              @RequestParam(value = "expedicionesSeleccionadas", required = false) List<Long> expedicionesIds) {
        if(expedicionesIds != null && !expedicionesIds.isEmpty()) {
            usuario.setExpediciones(expedicionServicio.findAllById(expedicionesIds));
        } else {
            usuario.setExpediciones(new ArrayList<>());
        }
        usuarioServicio.edit(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuario/{id}")
    public String mostrarDetalleUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.findById(id);
        
        if(usuario == null) {
            return "redirect:/usuarios";
        }
        
        double totalGastado = usuarioServicio.calcularGastado(usuario);
        boolean tieneDescuento = usuarioServicio.tieneDescuentoTerceraExpedicion(usuario);
        boolean esCumpleanios = usuarioServicio.esCumpleanios(usuario);
        
        int[] expedicionesPorCategoria = new int[4];
        double[] gastoPorCategoria = new double[4];
        
        for (Expedicion exp : usuario.getExpediciones()) {
            int categoria = exp.getCategoria();
            if (categoria >= 0 && categoria <= 3) {
                expedicionesPorCategoria[categoria]++;
                gastoPorCategoria[categoria] += exp.getPrecio();
            }
        }
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalGastado", totalGastado);
        model.addAttribute("tieneDescuento", tieneDescuento);
        model.addAttribute("esCumpleanios", esCumpleanios);
        model.addAttribute("expPorCategoria", expedicionesPorCategoria);
        model.addAttribute("gastoPorCategoria", gastoPorCategoria);
        
        return "detalleUsuario";
    }
   
    @PostMapping("/usuario/eliminar")
    public String eliminarUsuario(@RequestParam Long id) {
        usuarioServicio.deleteById(id);
        return "redirect:/usuarios";
    }
}