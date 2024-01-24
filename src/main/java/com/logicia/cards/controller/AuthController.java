package com.logicia.cards.controller;

import com.logicia.cards.dao.JWTAuthResponse;
import com.logicia.cards.dao.LoginDto;
import com.logicia.cards.dao.RegisterDto;
import com.logicia.cards.dao.SignUpRequest;
import com.logicia.cards.model.Role;
import com.logicia.cards.model.User;
import com.logicia.cards.repository.RoleRepository;
import com.logicia.cards.repository.UserRepository;
import com.logicia.cards.service.AuthService;
import com.logicia.cards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

        @Autowired
        UserService userService;
         @Autowired
         UserRepository userRepository;
         @Autowired
         RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest){
        return authService.register(signUpRequest);
    }



    @PutMapping("update")
    public ResponseEntity<?> updateUser(User user){
        return userService.addUser(user);

    }
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return userService.findUserById(id);

    }
    @GetMapping("list/all")
    public ResponseEntity<?> findAll(){
        return userService.findAll();

    }
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return userService.deleteById(id);

    }
}
