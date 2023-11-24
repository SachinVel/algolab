package com.enpm613.algolab.controller;

import com.enpm613.algolab.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @GetMapping("/getusers")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public ResponseEntity<Object> getUser() {

        //It's a sample request to
        logger.debug("Inside getUsers");
        return ResponseEntity.ok("true");
    }
}
