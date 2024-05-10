package com.example.api.web1.models.dao;

import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    //esta consulta es igual a la anterior
    @Query("select u from User u where u.username=?1")
    public User findbyUsername2(String username);

    @Query("from Inmueble")
    public List<Inmueble> findAllInmuebles();
}
