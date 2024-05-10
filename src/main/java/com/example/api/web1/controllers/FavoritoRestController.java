package com.example.api.web1.controllers;

import com.example.api.web1.models.dto.FavoritoDTO;
import com.example.api.web1.models.entity.Favorito;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.service.favorito.IFavoritoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FavoritoRestController {
    @Autowired
    private IFavoritoService favoritoService;
    @GetMapping("/favoritos")
    public ResponseEntity<List<FavoritoDTO>> getAllFavoritos() {
        List<FavoritoDTO> favoritos = favoritoService.getAllFavoritos();
        return new ResponseEntity<>(favoritos, HttpStatus.OK);
    }
    @PostMapping("/favoritos")
    public ResponseEntity<?> addFavorito(@RequestParam Long userId, @RequestParam Long inmuebleId) {
        try {
            Favorito favorito = favoritoService.addFavorito(userId, inmuebleId);
            return new ResponseEntity<>(favorito, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Entidad no encontrada: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Violación de integridad de datos: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        // Agregar más manejo de errores según sea necesario
    }

    @GetMapping("/favoritos/user/{userId}")
    public ResponseEntity<List<Favorito>> getFavoritosByUserId(@PathVariable Long userId) {
        List<Favorito> favoritos = favoritoService.findFavoritosByUserId(userId);
        return ResponseEntity.ok(favoritos);
    }
}
