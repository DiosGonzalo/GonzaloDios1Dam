package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.UsuarioRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioServicio  extends ServiciosBase<Usuario,Long,UsuarioRepositorio>{
    

   
    public Usuario calcularDescuento(Usuario usuario){
        StringBuilder motivos = new StringBuilder();

        if(usuario.getExpediciones().size()>=3){
            usuario.setDescuento(usuario.getDescuento()+15);
            motivos.append("Descuento por 3 o mas expediciones")
        }else{
            usuario.setDescuento(usuario.getDescuento()+0);
        }

    }
    
    public double calcularGastado(Long id){
            final double[] total = {0};
            Usuario usuario = findById(id);
            usuario.getExpediciones().forEach(exp -> total[0] += exp.getPrecio());
            return total[0];
        }
    /***public double calcularGastadoDescuento(Long id){
        Usuario usuario = repositorio.findById(id);
        if(usuario.getDescuento()==0){
            return calcularGastado(id);
        }else{
            return calcularGastado(id)-(calcularGastado(id)*usuario.getDescuento())/100;
        }
    }

*/
}
