package com.example.api.web1.controllers;

import com.example.api.web1.models.entity.Chat;
import com.example.api.web1.models.entity.Mensaje;
import com.example.api.web1.models.service.chat.IChatService;
import com.example.api.web1.models.service.mensaje.IMensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MensajeRestController {

    @Autowired
    private IMensajeService mensajeService;

    @GetMapping("/mensajes")
    public List<Mensaje> index(){
        return mensajeService.findAll();
    }

    @PostMapping("/mensajes")
    public Mensaje create(@RequestBody Mensaje mensaje){
        return mensajeService.save(mensaje);
    }

    @GetMapping("/mensajes/{id}")
    public Mensaje show (@PathVariable Long id){
        return mensajeService.findById(id);
    }

    @PutMapping("/mensajes/{id}")
    public Mensaje update(@RequestBody Mensaje mensaje, @PathVariable Long id){
        Mensaje mensajeActual = mensajeService.findById(id);
        mensajeActual.setContenido(mensaje.getContenido());
        mensajeActual.setChat(mensaje.getChat());
        mensajeActual.setContenido(mensaje.getContenido());

        return mensajeService.save(mensajeActual);
    }

    @DeleteMapping("/mensajes/{id}")
    public void delete(@PathVariable Long id){
        mensajeService.delete(id);
    }
}


