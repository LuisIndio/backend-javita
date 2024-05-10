package com.example.api.web1.controllers;

import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.User;
import com.example.api.web1.models.service.inmueble.IInmuebleService;
import com.example.api.web1.models.service.uploadfile.IUploadFileService;
import com.example.api.web1.models.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InmuebleRestController {
    @Autowired
    private IInmuebleService inmuebleService;
    @Autowired
    private UserService userService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/inmuebles")
    public List<Inmueble> index(){
        return inmuebleService.findAll();
    }

    @PostMapping("/inmuebles")
    public ResponseEntity<?> addInmueble(@RequestBody Inmueble inmueble) {
        try {
            if (inmueble.getVendedorId() == null) {
                throw new EntityNotFoundException("ID de vendedor es necesario");
            }
            User vendedor = userService.findById(inmueble.getVendedorId());
            inmueble.setVendedor(vendedor);
            inmueble = inmuebleService.save(inmueble);
            return new ResponseEntity<>(inmueble, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/inmuebles/{id}")
    public Inmueble show (@PathVariable Long id){
        return inmuebleService.findById(id);
    }

    @PutMapping("/inmuebles/{id}")
    public Inmueble update(@RequestBody Inmueble inmueble, @PathVariable Long id){
        Inmueble inmuebleActual = inmuebleService.findById(id);
        inmuebleActual.setAdquisicion(inmueble.getAdquisicion());
        inmuebleActual.setBaños(inmueble.getBaños());
        inmuebleActual.setDireccion(inmueble.getDireccion());
        inmuebleActual.setHabitaciones(inmueble.getHabitaciones());
        inmuebleActual.setDescripcion(inmueble.getDescripcion());
        inmuebleActual.setTipo(inmueble.getTipo());
        inmuebleActual.setPrecio(inmueble.getPrecio());
        inmuebleActual.setTamano_m2(inmueble.getTamano_m2());
        inmuebleActual.setAño_construccion(inmueble.getAño_construccion());
        return inmuebleService.save(inmuebleActual);
    }

    @DeleteMapping("/inmuebles/{id}")
    public void delete(@PathVariable Long id){
        inmuebleService.delete(id);
    }

    @PostMapping("/inmuebles/upload")
    public ResponseEntity<?> upload (@RequestParam("file")MultipartFile file, @RequestParam("id") Long id){
        Map<String,Object> response = new HashMap<>();
        Inmueble inmueble = inmuebleService.findById(id);
        if (!file.isEmpty()){
            String fileName = null;
            try{
                fileName = uploadFileService.copy(file);
            }catch (Exception e){
                response.put("message", "Error to upload image");
                response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String namePhotoBeforeDelete = inmueble.getImagen();
            uploadFileService.delete(namePhotoBeforeDelete);
            inmueble.setImagen(fileName);
            inmuebleService.save(inmueble);
            response.put("message", "Image uploaded successfully");
            response.put("customer", inmueble);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/inmuebles/uploads/img/{namePhoto:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String namePhoto){
        Resource resource = null;
        try{
            resource = uploadFileService.load(namePhoto);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource,header, HttpStatus.OK);
    }

}
