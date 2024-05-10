package com.example.api.web1.models.dao;

import com.example.api.web1.models.entity.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInmuebleDao extends JpaRepository<Inmueble, Long>{

}
