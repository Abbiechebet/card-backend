package com.logicia.cards.service;


import com.logicia.cards.dao.LoginDto;
import com.logicia.cards.dao.RegisterDto;
import com.logicia.cards.dao.SignUpRequest;
import com.logicia.cards.exception.BlogAPIException;
import com.logicia.cards.model.Role;
import com.logicia.cards.model.User;
import com.logicia.cards.repository.RoleRepository;
import com.logicia.cards.repository.UserRepository;
import com.logicia.cards.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity("Email Address already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity( "Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//        user.setPassword(signUpRequest.getPassword());
        Role userRole = roleRepository.findById(signUpRequest.getRoleId()).get();
        user.setRoles(Collections.singleton(userRole));
        user.setRoleId(signUpRequest.getRoleId());

        userRepository.save(user);

        return new ResponseEntity("User registered successfully",HttpStatus.ACCEPTED);
    }
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity("Email Address already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity( "Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(signUpRequest.getPassword());
        Role userRole = roleRepository.findByName("MEMBER").get();
        user.setRoles(Collections.singleton(userRole));


        userRepository.save(user);

        return new ResponseEntity("User registered successfully",HttpStatus.ACCEPTED);
    }
}
