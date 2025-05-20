package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.UsuarioRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioServicio extends ServiciosBase<Usuario, Long, UsuarioRepositorio> {

    public UsuarioServicio(UsuarioRepositorio repo) {
        super(repo);
    }

    public double calcularGastado(Usuario usuario) {
        if(usuario == null || usuario.getExpediciones() == null) 
            return 0.0;
            
        return usuario.getExpediciones().stream()
                .mapToDouble(Expedicion::getPrecio)
                .sum();
    }

    public boolean tieneDescuentoTerceraExpedicion(Usuario usuario) {
        return usuario != null && 
               usuario.getExpediciones() != null && 
               usuario.getExpediciones().size() >= 3;
    }

    public boolean esCumpleanios(Usuario usuario) {
        if(usuario == null || usuario.getFechaNacimiento() == null)
            return false;
            
        LocalDate hoy = LocalDate.now();
        LocalDate nacimiento = usuario.getFechaNacimiento();
        return nacimiento.getMonth() == hoy.getMonth() && 
               nacimiento.getDayOfMonth() == hoy.getDayOfMonth();
    }
}