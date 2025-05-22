package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Usuario;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.ExpedicionRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpedicionServicio extends ServiciosBase<Expedicion, Long, ExpedicionRepositorio> {

    private final UsuarioServicio usuarioServicio;

    public ExpedicionServicio(ExpedicionRepositorio repositorio, UsuarioServicio usuarioServicio) {
        super(repositorio);
        this.usuarioServicio = usuarioServicio;
    }

   public Optional<Expedicion> findByIdOptional(Long id) {
        return repositorio.findById(id);
    }

    public List<Expedicion> buscarExpedicion(String nombre) {
        return repositorio.buscarPorNombre(nombre);
    }

    public List<Expedicion> filtrarCategoria(int categoria) {
        return categoria == 4 ? repositorio.findAll() : repositorio.findByCategoria(categoria);
    }

    public List<Expedicion> ordenarPrecioMayor() {
        return repositorio.findAllOrderByPrecioDesc();
    }

    public List<Expedicion> ordenarPrecioMenor() {
        return repositorio.findAllOrderByPrecioAsc();
    }

    public List<Expedicion> ordenarFechaMayor() {
        return repositorio.findAllOrderByFechaAsc();
    }

    public List<Expedicion> ordenarFechaMenor() {
        return repositorio.findAllOrderByFechaDesc();
    }

    public void deleteById(Long id) {
        Expedicion expedicion = findById(id);
        if (expedicion != null) {
            List<Usuario> usuarios = usuarioServicio.findAll()
                .stream()
                .filter(u -> u.getExpediciones().contains(expedicion))
                .collect(Collectors.toList());
            
            for (Usuario usuario : usuarios) {
                usuario.getExpediciones().remove(expedicion);
                usuarioServicio.save(usuario);
            }
            repositorio.deleteById(id);
        }
    }

    public List<Expedicion> aplicarDescuentos(List<Expedicion> expediciones) {
        return expediciones.stream()
                .map(this::aplicarDescuentoIndividual)
                .collect(Collectors.toList());
    }
    public boolean usuarioTieneDescuento(Usuario usuario, Expedicion expedicion) {
        try {
            if (usuario == null || expedicion == null) {
                return false;
            }
            
            boolean terceraExpedicion = usuario.getExpediciones().size() >= 2;
            
            boolean esCumpleanios = usuario.getFechaNacimiento() != null &&
                                  usuario.getFechaNacimiento().getMonth() == LocalDate.now().getMonth() &&
                                  usuario.getFechaNacimiento().getDayOfMonth() == LocalDate.now().getDayOfMonth();
            
            return terceraExpedicion || esCumpleanios;
        } catch (Exception e) {
            return false;
        }
    }

    private Expedicion aplicarDescuentoIndividual(Expedicion exp) {
        if (exp.getPrecioOriginal() == null) {
            exp.setPrecioOriginal(exp.getPrecio());
        }

        double precioFinal = exp.getPrecioOriginal();
        StringBuilder motivos = new StringBuilder();
        boolean tieneDescuento = false;

        if (exp.getUsuarios().size() < exp.getCapacidad() * 0.4) {
            double descuento = precioFinal * 0.20;
            precioFinal -= descuento;
            motivos.append("20% por baja ocupación. ");
            tieneDescuento = true;
        }

        if (exp.getPrecioOriginal() > 10000) {
            double descuento = precioFinal * 0.15;
            precioFinal -= descuento;
            motivos.append("15% por precio alto. ");
            tieneDescuento = true;
        }

        if (exp.getFechaExpedicion().isBefore(LocalDate.now().plusMonths(6))) {
            double descuento = precioFinal * 0.10;
            precioFinal -= descuento;
            motivos.append("10% por fecha próxima. ");
            tieneDescuento = true;
        }

        if (tieneDescuento) {
            exp.setPrecio(precioFinal);
            exp.setMotivoDescuento(motivos.toString().trim());
        } else {
            exp.setPrecio(exp.getPrecioOriginal());
            exp.setMotivoDescuento(null);
        }

        return exp;
    }
}