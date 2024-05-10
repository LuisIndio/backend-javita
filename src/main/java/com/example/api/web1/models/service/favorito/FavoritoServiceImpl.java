package com.example.api.web1.models.service.favorito;

import com.example.api.web1.models.dao.FavoritoDao;
import com.example.api.web1.models.dao.IInmuebleDao;
import com.example.api.web1.models.dao.IUserDao;
import com.example.api.web1.models.dto.FavoritoDTO;
import com.example.api.web1.models.entity.Favorito;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritoServiceImpl implements IFavoritoService {

        @Autowired
        private FavoritoDao favoritoDao;
        @Autowired
        private IUserDao userDao;
        @Autowired
        private IInmuebleDao inmuebleDao;
        @Autowired
        private ModelMapper modelMapper;

        @Override
        public List<FavoritoDTO> getAllFavoritos() {
                List<Favorito> favoritos = favoritoDao.findAll();
                return favoritos.stream()
                        .map(this::convertToFavoritoDTO)
                        .collect(Collectors.toList());
        }

        private FavoritoDTO convertToFavoritoDTO(Favorito favorito) {
                return modelMapper.map(favorito, FavoritoDTO.class);
        }
        @Override
        public List<Favorito> findAll() {
                List<Favorito> favoritos = (List<Favorito>) favoritoDao.findAll();
                for (Favorito favorito : favoritos) {
                        Hibernate.initialize(favorito.getUser());
                        Hibernate.initialize(favorito.getInmueble());
                }
                return favoritos;
        }

        @Override
        public Favorito findById(Long id) {
            return favoritoDao.findById(id).orElse(null);
        }

        @Override
        public List<Favorito> findFavoritosByUserId(Long userId) {
                return favoritoDao.findByUserId(userId);
        }

        @Override
        public Favorito save(Favorito favorito) {
            return favoritoDao.save(favorito);
        }

        @Override
        public void delete(Long id) {
            favoritoDao.deleteById(id);
        }
        public Favorito addFavorito(Long userId, Long inmuebleId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Inmueble inmueble = inmuebleDao.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Inmueble no encontrado"));
        // Aquí podrías verificar si ya existe un favorito para esta combinación de usuario e inmueble
        Favorito favorito = new Favorito();
        favorito.setUser(user);
        favorito.setInmueble(inmueble);

        return favoritoDao.save(favorito);
    }

}
