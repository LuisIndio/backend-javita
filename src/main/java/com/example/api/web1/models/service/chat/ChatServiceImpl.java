package com.example.api.web1.models.service.chat;

import com.example.api.web1.models.dao.ChatDao;
import com.example.api.web1.models.dao.IInmuebleDao;
import com.example.api.web1.models.dao.IUserDao;
import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements IChatService {
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IInmuebleDao inmuebleDao;
    @Override
    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        return (List<Chat>) chatDao.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Chat findById(Long id) {
        return chatDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Chat save(Chat chat) {
        return chatDao.save(chat);
    }

    @Override
    public void delete(Long id) {
        chatDao.deleteById(id);
    }

    @Override
    @Transactional
    public Chat createChat(Long compradorId, Long inmuebleId) {
        // Encuentra el comprador y el inmueble utilizando sus respectivos ID
        User comprador = userDao.findById(compradorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con id: " + compradorId));
        Inmueble inmueble = inmuebleDao.findById(inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el inmueble con id: " + inmuebleId));

        // Obtiene el vendedor desde el inmueble
        User vendedor = inmueble.getVendedor();

        // Verifica si el vendedor es nulo antes de continuar
        if (vendedor == null) {
            throw new EntityNotFoundException("No se encontró el vendedor para el inmueble con id: " + inmuebleId);
        }

        // Verifica si ya existe un chat para el comprador, vendedor y el inmueble
        Optional<Chat> existingChat = chatDao.findChatByCompradorIdAndVendedorIdAndInmuebleId(compradorId, vendedor.getId(), inmuebleId);
        if (existingChat.isPresent()) {
            // Si el chat ya existe, devuelve ese chat
            return existingChat.get();
        } else {
            // Si no existe, crea un nuevo chat y lo guarda
            Chat newChat = new Chat();
            newChat.setComprador(comprador);
            newChat.setVendedor(vendedor);
            newChat.setInmueble(inmueble);
            // Aquí podrías configurar más propiedades si fuera necesario
            return chatDao.save(newChat);
        }
    }



    @Override
    public List<Chat> findAllChatsByUserId(Long userId) {
        // Aquí asumimos que el usuario siempre es el comprador.
        // Si tu lógica es diferente, ajusta según sea necesario.
        return chatDao.findByCompradorId(userId);
    }


    @Override
    public Chat findChatByUserIdAndInmueble(Long userId, Long inmuebleId) {
        return chatDao.findChatByCompradorIdAndInmuebleId(userId, inmuebleId)
                .orElseThrow(() -> new EntityNotFoundException("Chat no encontrado con usuario ID: " + userId + " y inmueble ID: " + inmuebleId));
    }

}
