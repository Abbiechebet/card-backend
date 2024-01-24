package com.logicia.cards.service;


import com.logicia.cards.dao.LoginDto;
import com.logicia.cards.dao.RegisterDto;
import com.logicia.cards.dao.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);

    ResponseEntity<?> register(SignUpRequest signUpRequest);
}
