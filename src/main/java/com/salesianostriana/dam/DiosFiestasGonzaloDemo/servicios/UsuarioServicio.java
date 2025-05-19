package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.UsuarioRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioServicio  extends ServiciosBase<Usuario,Long,UsuarioRepositorio>{
    

    public double calcularGastado(Long id){
        Usuario u = this.findById(id);
        final double[] total = {0};
        u.getExpediciones().forEach(exp ->
            total[0] += exp.getPrecio());
        return total[0];
    }

    public boolean descuentoTerceraExpedicion(Long id){
        Usuario u= this.findById(id);
        if(u.getExpediciones().size()>=3){
            return true;

    }else  {
        return false;
    }

    








    }
    

    }


