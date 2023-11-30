package com.enpm613.algolab.controller;

import com.enpm613.algolab.service.UserService;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @GetMapping("/getusers")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT','ADMIN')")
    public ResponseEntity<Object> getUser(@AuthenticationPrincipal UserDetails user) {
        //It's a sample request to
        logger.debug("Inside getUsers : " );
        return ResponseEntity.ok("true");
    }
}
