package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

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

    @GetMapping("/validate-username")
    public ResponseEntity<Boolean> validateUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.isValidUsername(username));
    }
    @GetMapping("/login")
    public ResponseEntity<Boolean> login() {
        System.out.println("login");
        return ResponseEntity.ok(true);
    }
}
