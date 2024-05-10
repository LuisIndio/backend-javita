package com.example.api.web1.models.dao;

import com.example.api.web1.models.entity.Favorito;
import com.example.api.web1.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoDao extends JpaRepository<Favorito, Long>{
    List<Favorito> findByUserId(Long userId);
}
