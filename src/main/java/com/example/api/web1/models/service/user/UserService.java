package com.example.api.web1.models.service.user;

import com.example.api.web1.auth.AuthResponse;
import com.example.api.web1.auth.LoginRequest;
import com.example.api.web1.auth.RegisterRequest;
import com.example.api.web1.models.dao.IUserDao;
import com.example.api.web1.models.entity.Inmueble;
import com.example.api.web1.models.entity.Role;
import com.example.api.web1.models.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest loginRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails user = userDao.findByUsername(loginRequest.getUsername()).orElseThrow();
        User user2 = userDao.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.createToken(user);
        return AuthResponse.builder()
                .token(token)
                .userID(user2.getId())
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .lastname(registerRequest.getLastname())
                .phone(registerRequest.getPhone())
                .role(Role.USER)
                .build();

        userDao.save(user);

        return AuthResponse.builder()
                .token(jwtService.createToken(user))
                .build();
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inmueble> findAllInmuebles() {
        return userDao.findAllInmuebles();
    }
}
