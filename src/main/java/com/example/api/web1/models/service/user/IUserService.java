package com.example.api.web1.models.service.user;

import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public User findById(Long id);
    public void delete(Long id);
    public List<Inmueble> findAllInmuebles();
}
