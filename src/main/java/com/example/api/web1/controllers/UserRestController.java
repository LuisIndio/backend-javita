package com.example.api.web1.controllers;

import com.example.api.web1.auth.AuthResponse;
import com.example.api.web1.auth.LoginRequest;
import com.example.api.web1.auth.RegisterRequest;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://localhost:3000"})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("/customers/regions")
    public List<Inmueble> listinmuebles(){
        return authService.findAllInmuebles();
    }
}
