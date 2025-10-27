package com.example.notesapplication.controller;

import com.example.notesapplication.model.User;
import com.example.notesapplication.service.UserDetailsServiceImpl;
import com.example.notesapplication.service.UserService;
import com.example.notesapplication.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signupUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            Map<String, String> jsonJwt = new HashMap<>(Map.of());
            jsonJwt.put("token", jwt);
            return new ResponseEntity<>(jsonJwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<>(Map.of("error", "Incorrect username and password")), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }
}