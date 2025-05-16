package com.salesianostriana.dam.DiosFiestasGonzaloDemo.controladores;

import java.util.ArrayList;
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
@RequiredArgsConstructor
@Controller
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
@PostMapping("/usuario/editar/{id}")
public String verEdicionUsuario(Model modal, Long id){
    Usuario usuario= usuarioServicio.findById(id);
    modal.addAttribute("usuario",usuario);
    modal.addAttribute("expediciones", expedicionServicio.findAll());
    return "usuarios";

}
@GetMapping("/usuario/editar/{id}/submit")
public String editarUsuario(@ModelAttribute Usuario usuario, @RequestParam("expedicionesSeleccionadas") List<Long> expedicionesIds) {
    if(expedicionesIds != null && !expedicionesIds.isEmpty()){
        List<Expedicion> expediciones = expedicionServicio.findAllById(expedicionesIds);
        usuario.setExpediciones(expediciones);
    }
    usuarioServicio.save(usuario);
    return "redirect:/usuarios";    
    }
@GetMapping("/usuario/{id}")
public String verUsuario(@RequestParam Long id, Model model){
    Usuario usuario = usuarioServicio.findById(id);
    model.addAttribute("usuario", usuario);
    return "detalleUsuario";
}


@PostMapping("/usuario/editar")
public String editarUsuario(@ModelAttribute Usuario usuario,@RequestParam ("expediciones") List<Long> expedicionesIds,
    Model model) {
    
    if (expedicionesIds != null) {
        List<Expedicion> expediciones = expedicionServicio.findAllById(expedicionesIds);
        usuario.setExpediciones(expediciones);
    } else {
        usuario.setExpediciones(new ArrayList<>());
    }
    
    usuarioServicio.save(usuario);
    return "redirect:/usuarios";
}

@PostMapping("/usuario/borrar")
    public String borrarUsuario(Model model,@RequestParam Long id){
        usuarioServicio.deleteById(id);
        return "redirect:/usuarios";
    }

@GetMapping("/usuarios/{id}")
public String verUsuario(Long id){
    Usuario usuario = usuarioServicio.findById(id);
    
    if(usuario != null) {
        return "detalleUsuario";
    } else {
        return "redirect:/usuarios";
}
}
}
