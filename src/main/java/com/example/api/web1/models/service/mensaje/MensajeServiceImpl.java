package com.example.api.web1.models.service.mensaje;

import com.example.api.web1.models.dao.MensajeDao;
import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Mensaje;
import com.example.api.web1.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MensajeServiceImpl implements IMensajeService{
    @Autowired
    private MensajeDao mensajeDao;
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> findAll() {
        return (List<Mensaje>) mensajeDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Mensaje findById(Long id) {
        return mensajeDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Mensaje save(Mensaje mensaje) {
        return mensajeDao.save(mensaje);
    }

    @Override
    public void delete(Long id) {
        mensajeDao.deleteById(id);
    }
    @Override
    public Mensaje saveMessage(Chat chat, User userEnvio, String contenido) {
        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setUser(userEnvio);
        mensaje.setContenido(contenido);

        return mensajeDao.save(mensaje);
    }

    @Override
    public List<Mensaje> findMessagesByChatId(Long id) {
        return mensajeDao.findByChatChatId(id);
    }
}
