package com.example.api.web1.models.dao;

import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatDao extends JpaRepository<Chat, Long> {

    Optional<Chat> findChatByCompradorIdAndInmuebleId(Long userId, Long inmuebleId);

    List<Chat> findByCompradorId(Long userId);

    Optional<Chat> findChatByCompradorIdAndVendedorIdAndInmuebleId(Long compradorId, Long vendedorId, Long inmuebleId);

}
