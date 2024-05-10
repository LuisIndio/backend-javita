package com.example.api.web1.models.service.inmueble;

import com.example.api.web1.models.entity.Inmueble;

import java.util.List;

public interface IInmuebleService {
    public List<Inmueble> findAll();
    public Inmueble findById(Long id);
    public Inmueble save(Inmueble inmueble);
    public void delete(Long id);

}
