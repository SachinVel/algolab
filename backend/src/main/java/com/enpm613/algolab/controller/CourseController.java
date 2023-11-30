package com.enpm613.algolab.controller;

import com.enpm613.algolab.entity.Course;
import com.enpm613.algolab.entity.CourseDTO;
import com.enpm613.algolab.entity.User;
import com.enpm613.algolab.service.CourseService;
import com.enpm613.algolab.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/allCourses")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT','ADMIN')")
    public ResponseEntity<Object> getAllCourses() {
        //It's a sample request to
        logger.debug("Inside getUsers : " );
        return ResponseEntity.ok("true");
    }

    @GetMapping("/userCourses")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT','ADMIN')")
    public ResponseEntity<Object> getUserCourses(@AuthenticationPrincipal UserDetails user) {
        //It's a sample request to
        logger.debug("Inside getUsers : " );
        return ResponseEntity.ok("true");
    }

    @PostMapping("/createCourse")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public ResponseEntity<Object> createCourse(@AuthenticationPrincipal UserDetails user,  @ModelAttribute CourseDTO newCourse) throws IOException {
        //It's a sample request to
        logger.debug("Inside createCourse : " );
        String curUsername = user.getUsername();
        User curUser = userService.getUserByUsername(curUsername);

        Course createdCourse = courseService.createCourse(newCourse,curUser);

        return ResponseEntity.ok("true");
    }

    @DeleteMapping("/deleteCourse")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public ResponseEntity<Object> deleteCourse(@AuthenticationPrincipal UserDetails user,@RequestParam String courseId) throws  IOException{
        //It's a sample request to
        logger.debug("Inside getUsers : " );
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("true");
    }


}
