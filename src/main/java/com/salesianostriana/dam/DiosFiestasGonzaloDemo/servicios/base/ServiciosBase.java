package com.salesianostriana.dam.DiosFiestasGonzaloDemo.servicios.base;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ServiciosBase<T, ID, R extends JpaRepository<T, ID>> {

    protected R repositorio;
    
    public ServiciosBase(R repo) {
        this.repositorio = repo;
    }
    
    public T save(T t) {
        return repositorio.save(t);
    }
    
    public T findById(ID id) {
        Optional<T> result = repositorio.findById(id);
        return result.orElse(null);
    }
    
    public List<T> findAll() {
        return repositorio.findAll();
    }
    
    public List<T> findAllById(Iterable<ID> ids) {
        return repositorio.findAllById(ids);
    }
    
    public T edit(T t) {
        return repositorio.save(t);
    }
    
    public void delete(T t) {
        repositorio.delete(t);
    }
    
    public void deleteById(ID id) {
        repositorio.deleteById(id);
    }
}