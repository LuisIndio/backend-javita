package com.example.api.web1.models.service.chat;

import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;

import java.util.List;

public interface IChatService {
    public List<Chat> findAll();
    public Chat findById(Long id);
    public Chat save(Chat chat);
    public void delete(Long id);
    public List<Chat> findAllChatsByUserId(Long id);
    public Chat findChatByUserIdAndInmueble(Long idUser, Long idInmueble);
    public Chat createChat(Long compradorId, Long inmuebleId);

}
