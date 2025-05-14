package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.ExpedicionServicio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.UsuarioServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;
    private final ExpedicionServicio expedicionServicio;

    @GetMapping("/usuarios")
    public String listaUsuarios(Model model){
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "usuarios";
    }

    @GetMapping("/usuario/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("expediciones", expedicionServicio.findAll());
        return "agregarUsuario";
    }

    @PostMapping("/usuario/nuevo/submit")
    public String guardarUsuario( @ModelAttribute Usuario usuario,  @RequestParam("expedicionesSeleccionadas") List<Long> expedicionesIds) {
       if(expedicionesIds != null && !expedicionesIds.isEmpty()){
            List<Expedicion> expediciones = expedicionServicio.findAllById(expedicionesIds);
            usuario.setExpediciones(expediciones);
        }
        usuarioServicio.save(usuario);
        return "redirect:/usuarios";
    }
}
