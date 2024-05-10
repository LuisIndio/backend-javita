package com.example.api.web1.models.service.favorito;

import com.example.api.web1.models.dto.FavoritoDTO;
import com.example.api.web1.models.entity.Favorito;

import java.util.List;

public interface IFavoritoService {
    public List<Favorito> findAll();
    public Favorito findById(Long id);
    public Favorito save(Favorito favorito);
    public void delete(Long id);
    public Favorito addFavorito(Long userid, Long inmuebleid);

    List<FavoritoDTO> getAllFavoritos();

    public List<Favorito> findFavoritosByUserId(Long id);
}
