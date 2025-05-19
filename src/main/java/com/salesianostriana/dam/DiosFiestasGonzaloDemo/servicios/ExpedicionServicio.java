package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios;

import com.salesianostriana.dam.DiosFiestasGonzaloDemo.modelos.Expedicion;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.repositorio.ExpedicionRepositorio;
import com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base.ServiciosBase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpedicionServicio extends ServiciosBase<Expedicion, Long, ExpedicionRepositorio> {

    public List<Expedicion> buscarExpedicion(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return repositorio.findAll();
        }
        return repositorio.findAll().stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Expedicion> filtrarCategoria(int categoria) {
        return categoria == 4 ? 
            repositorio.findAll() : 
            repositorio.findAll().stream()
                .filter(e -> e.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<Expedicion> ordenarPrecioMayor() {
        return repositorio.findAll().stream()
                .sorted(Comparator.comparing(Expedicion::getPrecio).reversed())
                .collect(Collectors.toList());
    }

    public List<Expedicion> ordenarPrecioMenor() {
        return repositorio.findAll().stream()
                .sorted(Comparator.comparing(Expedicion::getPrecio))
                .collect(Collectors.toList());
    }

    public List<Expedicion> ordenarFechaMayor() {
        return repositorio.findAll().stream()
                .sorted(Comparator.comparing(Expedicion::getFechaExpedicion))
                .collect(Collectors.toList());
    }

    public List<Expedicion> ordenarFechaMenor() {
        return repositorio.findAll().stream()
                .sorted(Comparator.comparing(Expedicion::getFechaExpedicion).reversed())
                .collect(Collectors.toList());
    }

    public List<Expedicion> aplicarDescuentos(List<Expedicion> expediciones) {
        return expediciones.stream()
                .map(this::aplicarDescuentoIndividual)
                .collect(Collectors.toList());
    }

    private Expedicion aplicarDescuentoIndividual(Expedicion exp) {
        if (exp.getPrecioOriginal() == null) {
            exp.setPrecioOriginal(exp.getPrecio());
        }

        double precioFinal = exp.getPrecioOriginal();
        StringBuilder motivos = new StringBuilder();

        if (exp.getUsuarios().size() < exp.getCapacidad() * 0.4) {
            double descuento = precioFinal * 0.20;
            precioFinal -= descuento;
            motivos.append("20% por baja ocupación. ");
        }

        if (exp.getPrecioOriginal() > 10000) {
            double descuento = precioFinal * 0.15;
            precioFinal -= descuento;
            motivos.append("15% por precio alto. ");
        }

        if (exp.getFechaExpedicion().isBefore(LocalDate.now().plusMonths(6))) {
            double descuento = precioFinal * 0.10;
            precioFinal -= descuento;
            motivos.append("10% por fecha próxima. ");
        }

        if (motivos.length() > 0) {
            exp.setPrecio(precioFinal);
            exp.setMotivoDescuento(motivos.toString().trim());
        }

        return exp;
    }
}