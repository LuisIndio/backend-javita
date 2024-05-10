package com.example.api.web1.models.service.inmueble;

import com.example.api.web1.models.dao.IInmuebleDao;
import com.example.api.web1.models.entity.Inmueble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InmuebleServiceImpl implements IInmuebleService{
    @Autowired
    private IInmuebleDao inmuebleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Inmueble> findAll() {
        return (List<Inmueble>) inmuebleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Inmueble findById(Long id) {
        return inmuebleDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Inmueble save(Inmueble inmueble) {
        return inmuebleDao.save(inmueble);
    }

    @Override
    public void delete(Long id) {
        inmuebleDao.deleteById(id);
    }
}
