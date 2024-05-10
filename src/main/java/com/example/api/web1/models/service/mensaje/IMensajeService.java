package com.example.api.web1.models.service.mensaje;

import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Mensaje;
import com.example.api.web1.models.entity.User;

import java.util.List;

public interface IMensajeService {
    public List<Mensaje> findAll();
    public Mensaje findById(Long id);
    public Mensaje save(Mensaje mensaje);
    public void delete(Long id);
    Mensaje saveMessage(Chat chat, User userEnvio, String contenido);
    public List<Mensaje> findMessagesByChatId(Long id);

}
