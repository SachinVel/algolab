package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.entity.AuthRequest;
import com.enpm613.algolab.entity.LoginResponse;
import com.enpm613.algolab.service.JWTService;
import com.enpm613.algolab.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity.ok(userService.getUser(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.isValidUsername(username));
    }



    @PostMapping("/login")
    public LoginResponse AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO){
        logger.debug("Inside login post method");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        String role = userService.getUserByUsername(authRequestDTO.getUsername()).getRole().toString();
        if(authentication.isAuthenticated()){
            return LoginResponse.builder()
                    .token(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .role(role)
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
