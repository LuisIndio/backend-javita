package com.example.api.web1.controllers;

import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.Mensaje;
import com.example.api.web1.models.entity.User;
import com.example.api.web1.models.service.chat.IChatService;
import com.example.api.web1.models.service.inmueble.IInmuebleService;
import com.example.api.web1.models.service.mensaje.IMensajeService;
import com.example.api.web1.models.service.uploadfile.IUploadFileService;
import com.example.api.web1.models.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRestController {

    @Autowired
    private IChatService chatService;
    @Autowired
    private IInmuebleService inmuebleService;
    @Autowired
    private IMensajeService messageService;
    @Autowired
    private UserService userService;

    @GetMapping("/chats")
    public List<Chat> index(){
        return chatService.findAll();
    }
    @PostMapping("/chats")
    public Chat create(@RequestBody Chat chat){
        return chatService.save(chat);
    }

    @GetMapping("/chats/{id}")
    public Chat show (@PathVariable Long id){
        return chatService.findById(id);
    }

    @PutMapping("/chats/{id}")
    public Chat update(@RequestBody Chat chat, @PathVariable Long id){
        Chat chatActual = chatService.findById(id);
        chatActual.setComprador(chat.getComprador());
        chatActual.setInmueble(chat.getInmueble());

        return chatService.save(chatActual);
    }
    // Crear un nuevo chat
    @PostMapping("/chats/create")
    public ResponseEntity<?> createChat(@RequestParam Long compradorId, @RequestParam Long inmuebleId) {
        try {
            Chat chat = chatService.createChat(compradorId, inmuebleId);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejar excepciones, como entidades no encontradas o errores de base de datos
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Listar todos los chats de un usuario
    @GetMapping("chats/user/{userId}")
    public ResponseEntity<List<Chat>> listChatsForUser(@PathVariable Long userId) {
        List<Chat> chats = chatService.findAllChatsByUserId(userId);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    // Enviar un mensaje en un chat
    @PostMapping("chats/{chatId}/send")
    public ResponseEntity<?> sendMessage(@PathVariable Long chatId,
                                         @RequestParam Long userId,
                                         @RequestParam String content) {
        try {
            Chat chat = chatService.findById(chatId);
            User userEnvio = userService.findById(userId);
            Mensaje message = messageService.saveMessage(chat, userEnvio, content);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejar excepciones, como entidades no encontradas o errores de base de datos
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener los mensajes de un chat
    @GetMapping("chats/{chatId}/messages")
    public ResponseEntity<List<Mensaje>> getMessagesForChat(@PathVariable Long chatId) {
        List<Mensaje> messages = messageService.findMessagesByChatId(chatId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    @GetMapping("/chats/user/{userId}/inmueble/{inmuebleId}")
    public ResponseEntity<?> findChatByUserAndInmueble(@PathVariable Long userId, @PathVariable Long inmuebleId) {
        try {
            Chat chat = chatService.findChatByUserIdAndInmueble(userId, inmuebleId);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/chats/{id}")
    public void delete(@PathVariable Long id){
        chatService.delete(id);
    }
}
