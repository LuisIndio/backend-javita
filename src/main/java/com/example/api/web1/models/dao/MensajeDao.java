package com.example.api.web1.models.dao;

import com.example.api.web1.models.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeDao extends JpaRepository<Mensaje, Long>{
    List<Mensaje> findByChatChatId(Long chatId);

}
